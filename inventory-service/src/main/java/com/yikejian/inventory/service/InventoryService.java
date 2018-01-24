package com.yikejian.inventory.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.inventory.domain.inventory.Inventory;
import com.yikejian.inventory.domain.inventory.InventoryEvent;
import com.yikejian.inventory.domain.product.Product;
import com.yikejian.inventory.domain.store.Device;
import com.yikejian.inventory.domain.store.Store;
import com.yikejian.inventory.domain.store.StoreProduct;
import com.yikejian.inventory.exception.InventoryServiceException;
import com.yikejian.inventory.repository.InventoryEventRepository;
import com.yikejian.inventory.repository.InventoryRepository;
import com.yikejian.inventory.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <code>InventoryService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Service
public class InventoryService {

    private InventoryRepository inventoryRepository;
    private InventoryEventRepository inventoryEventRepository;
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Value("${yikejian.api.product.url}")
    private String productUrl;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository,
                            InventoryEventRepository inventoryEventRepository,
                            OAuth2RestTemplate oAuth2RestTemplate) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryEventRepository = inventoryEventRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @HystrixCommand
    synchronized public Inventory getInventoryById(Long inventoryId) {
        Inventory inventory = inventoryRepository.findByInventoryId(inventoryId);
        Flux<InventoryEvent> inventoryEvents =
                Flux.fromStream(inventoryEventRepository.findByInventoryId(inventory.getInventoryId()));
        Inventory newInventory = inventoryEvents.reduceWith(() -> inventory, Inventory::incorporate).get();
//        if (!inventory.equals(newInventory)) {
//            saveInventory(inventory);
//        }
        return newInventory;
    }

    @HystrixCommand
    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @HystrixCommand
    public List<Inventory> saveInventories(List<Inventory> inventoryList) {
        return (List<Inventory>) inventoryRepository.save(inventoryList);
    }

    private Inventory transInventory(Inventory inventory) {
        Inventory newInventory = inventory;
        if (inventory.getInventoryId() != null) {
            Inventory oldInventory = inventoryRepository.findByInventoryId(inventory.getInventoryId());
            newInventory = oldInventory.mergeOtherInventory(inventory);
        }
        return newInventory;
    }

    @HystrixCommand
    synchronized public List<Inventory> getInventories(Inventory params) {
        List<Inventory> inventoryList = inventoryRepository.findAll(inventorySpec(params));
        List<Inventory> newInventoryList = Lists.newArrayList();
//        List<Inventory> changedInventoryList = Lists.newArrayList();
        for (Inventory inventory : inventoryList) {
            Flux<InventoryEvent> inventoryEvents =
                    Flux.fromStream(inventoryEventRepository.findByInventoryId(inventory.getInventoryId()));
            Inventory newInventory = inventoryEvents.reduceWith(() -> inventory, Inventory::incorporate).get();
//            if (!inventory.equals(newInventory)) {
//                changedInventoryList.add(newInventory);
//            }
            newInventoryList.add(newInventory);
        }
//        saveInventories(changedInventoryList);
        return newInventoryList;
    }

    private Specification<Inventory> inventorySpec(final Inventory inventory) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (inventory != null) {
                if (StringUtils.isNotBlank(inventory.getDay())) {
                    predicateList.add(cb.equal(root.get("day").as(String.class), inventory.getDay()));
                }
                if (inventory.getStoreId() != null) {
                    predicateList.add(cb.equal(root.get("storeId").as(Integer.class), inventory.getStoreId()));
                }
                if (inventory.getProductId() != null) {
                    predicateList.add(cb.equal(root.get("productId").as(Integer.class), inventory.getProductId()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

    public Boolean initInventoryOfStore(Store store) {
        LocalDate currentDate = LocalDate.now();
        int i = 0;
        while (i < 5) {
            String day = DateUtils.dateToDayStr(currentDate);
            currentDate = currentDate.plusDays(1);
            initInventoryOfStore(store, day);
            i++;
        }
        return true;
    }

    public Boolean initInventoryOfStore(Store store, String day) {
        if (store == null) {
            String msg = "init inventory error. store is null.";
            throw new InventoryServiceException(msg);
        }
        try {
            DateUtils.dayStrToDate(day);
        } catch (RuntimeException e) {
            throw new InventoryServiceException(e.getLocalizedMessage());
        }
        Long storeId = store.getStoreId();
        Integer unitDuration = store.getUnitDuration();
        List<Inventory> storeInventoryList = inventoryRepository.findByStoreIdAndDay(storeId, day);
        Set<Device> deviceSet = store.getDeviceSet();
        for (StoreProduct storeProduct : store.getStoreProductSet()) {
            Long productId = storeProduct.getProductId();
            List<Inventory> productInventoryList = inventoryRepository.findByStoreIdAndProductIdAndDay(storeId, productId, day);
            for(Inventory productInventory: productInventoryList){
                productInventory.setEffective(storeProduct.getEffective());
                productInventory.setDeleted(storeProduct.getDeleted());
            }

        }

        return true;
    }

    public Boolean initInventoryOfStoreOld(Store store, String day) {
        if (store == null) {
            String msg = "init inventory error. store is null.";
            throw new InventoryServiceException(msg);
        }
        try {
            DateUtils.dayStrToDate(day);
        } catch (RuntimeException e) {
            throw new InventoryServiceException(e.getLocalizedMessage());
        }
        Long storeId = store.getStoreId();
        Integer unitDuration = store.getUnitDuration();
        for (StoreProduct storeProduct : store.getStoreProductSet()) {
            Long productId = storeProduct.getProductId();
//            List<Inventory> inventoryList = inventoryRepository.findByStoreIdAndProductIdAndDay(storeId, productId, day);
            Long exists = inventoryRepository.countByStoreIdAndProductIdAndDay(storeId, productId, day);
            if (exists == 0) {
                Product product = oAuth2RestTemplate.getForObject(productUrl + "/" + productId, Product.class);
                if (product == null) {
                    throw new InventoryServiceException("Not found the product for id:" + productId);
                }
                List<String> pieceTimeList = DateUtils.generatePieceTimeOfDay(
                        day,
                        store.getStartTime().compareTo(product.getStartTime()) > 0 ? product.getStartTime() : store.getStartTime(),
                        store.getEndTime().compareTo(product.getEndTime()) > 0 ? product.getEndTime() : store.getEndTime(),
                        unitDuration
                );
                List<Inventory> inventoryList = Lists.newArrayList();
                for (String pieceTime : pieceTimeList) {
                    inventoryList.add(new Inventory(storeId, productId, store.getUnitTimes(), day, pieceTime));
                }
                inventoryRepository.save(inventoryList);
            }
        }

        return true;
    }

    public InventoryEvent addInventoryEvent(InventoryEvent inventoryEvent) {
        return inventoryEventRepository.save(inventoryEvent);
    }

}
