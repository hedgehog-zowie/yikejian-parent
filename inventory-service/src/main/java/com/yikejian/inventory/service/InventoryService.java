package com.yikejian.inventory.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.inventory.domain.inventory.Inventory;
import com.yikejian.inventory.domain.inventory.InventoryEvent;
import com.yikejian.inventory.domain.store.Device;
import com.yikejian.inventory.domain.store.DeviceProduct;
import com.yikejian.inventory.domain.store.Store;
import com.yikejian.inventory.domain.store.StoreProduct;
import com.yikejian.inventory.exception.InventoryServiceException;
import com.yikejian.inventory.repository.InventoryEventRepository;
import com.yikejian.inventory.repository.InventoryRepository;
import com.yikejian.inventory.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    private static final Integer INIT_DAYS = 5;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository,
                            InventoryEventRepository inventoryEventRepository) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryEventRepository = inventoryEventRepository;
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
        return inventoryRepository.save(transform(inventory));
    }

    @HystrixCommand
    public List<Inventory> saveInventories(List<Inventory> inventoryList) {
        return (List<Inventory>) inventoryRepository.save(
                Lists.newArrayList(inventoryList.stream().
                        map(this::transform).collect(Collectors.toList()))
        );
    }

    private Inventory transform(Inventory inventory) {
        Inventory newInventory = inventory;
        if (inventory.getProductId() != null) {
            Inventory oldInventory = inventoryRepository.findByInventoryId(inventory.getInventoryId());
            newInventory = oldInventory.mergeOther(inventory);
        }
        return newInventory;
    }

    private Inventory transInventory(Inventory inventory) {
        Inventory newInventory = inventory;
        if (inventory.getInventoryId() != null) {
            Inventory oldInventory = inventoryRepository.findByInventoryId(inventory.getInventoryId());
            newInventory = oldInventory.mergeOther(inventory);
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

    @Transactional
    public List<Inventory> initInventoryOfStore(Store store) {
        LocalDate currentDate = LocalDate.now();
        List<Inventory> allInventorySet = Lists.newArrayList();
        int i = 0;
        while (i < INIT_DAYS) {
            String day = DateUtils.dateToDayStr(currentDate);
            currentDate = currentDate.plusDays(1);
            allInventorySet.addAll(initInventoryOfStore(store, day));
            i++;
        }
        return allInventorySet;
    }

    @Transactional
    public List<Inventory> initInventoryOfStore(Store store, String day) {
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
        Set<Device> deviceSet = store.getDeviceSet();
        Map<Long, Integer> productDeviceNums = getProductDeviceNum(deviceSet);
        Set<Inventory> allInventorySet = Sets.newHashSet();
        for (StoreProduct storeProduct : store.getStoreProductSet()) {
            Long productId = storeProduct.getProductId();
            inventoryRepository.deleteByStoreIdAndProductIdAndDay(storeId, productId, day);
//            Set<Inventory> oldInventorySet = inventoryRepository.findByStoreIdAndProductIdAndDay(storeId, productId, day);
//            for (Inventory productInventory : oldInventorySet) {
//                productInventory.setEffective(storeProduct.getEffective());
//                productInventory.setDeleted(storeProduct.getDeleted());
//            }
            List<String> pieceTimeList = DateUtils.generatePieceTimeOfDay(
                    day,
                    store.getStartTime().compareTo(storeProduct.getStartTime()) < 0 ? storeProduct.getStartTime() : store.getStartTime(),
                    store.getEndTime().compareTo(storeProduct.getEndTime()) > 0 ? storeProduct.getEndTime() : store.getEndTime(),
                    unitDuration
            );
            Set<Inventory> newInventorySet = Sets.newHashSet();
            Integer stock = productDeviceNums.get(productId) == null ? 0 : productDeviceNums.get(productId);
            for (String pieceTime : pieceTimeList) {
                Inventory inventory = new Inventory(storeId, productId, store.getUnitTimes() > stock ? stock : store.getUnitTimes(), day, pieceTime);
//                inventory.setEffective(storeProduct.getEffective());
//                inventory.setDeleted(storeProduct.getDeleted());
                newInventorySet.add(inventory);
            }
            Set<Inventory> mergedInventorySet = Sets.newHashSet();
            mergedInventorySet.addAll(newInventorySet);
//            mergedInventorySet.addAll(oldInventorySet);
            allInventorySet.addAll(mergedInventorySet);
        }

        return (List<Inventory>) inventoryRepository.save(allInventorySet);
    }

    /**
     * 获得各个产品支持的设备数量
     * @param deviceSet
     */
    public Map<Long, Integer> getProductDeviceNum(Set<Device> deviceSet){
        Map<Long, Integer> productDeviceNums = Maps.newHashMap();
        for (Device device : deviceSet) {
            for (DeviceProduct deviceProduct : device.getDeviceProductSet()) {
                if (deviceProduct.getDeleted() == 1 || deviceProduct.getEffective() == 0) {
                    continue;
                }
                Long productId = deviceProduct.getProductId();
                Integer number = productDeviceNums.get(productId);
                if (number == null) {
                    productDeviceNums.put(productId, 0);
                }
                productDeviceNums.put(productId, productDeviceNums.get(productId) + 1);
            }
        }
        return productDeviceNums;
    }

    public InventoryEvent addInventoryEvent(InventoryEvent inventoryEvent) {
        return inventoryEventRepository.save(inventoryEvent);
    }

}
