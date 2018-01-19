package com.yikejian.inventory.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.inventory.domain.inventory.Inventory;
import com.yikejian.inventory.domain.inventory.InventoryEvent;
import com.yikejian.inventory.repository.InventoryEventRepository;
import com.yikejian.inventory.repository.InventoryRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository,
                            InventoryEventRepository inventoryEventRepository) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryEventRepository = inventoryEventRepository;
    }

    @HystrixCommand
    public Inventory getInventoryById(Long inventoryId) {
        Inventory inventory = inventoryRepository.findByInventoryId(inventoryId);
        Flux<InventoryEvent> inventoryEvents =
                Flux.fromStream(inventoryEventRepository.findByInventoryId(inventory.getInventoryId()));
        return inventoryEvents.reduceWith(() -> inventory, Inventory::incorporate).get();
    }

    @HystrixCommand
    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @HystrixCommand
    public List<Inventory> saveInventories(List<Inventory> inventoryList) {
        return (List<Inventory>) inventoryRepository.save(inventoryList);
    }

    @HystrixCommand
    public List<Inventory> getInventories(Inventory params) {
        List<Inventory> inventoryList = inventoryRepository.findAll(inventorySpec(params));
        for (Inventory inventory : inventoryList) {
            Flux<InventoryEvent> inventoryEvents =
                    Flux.fromStream(inventoryEventRepository.findByInventoryId(inventory.getInventoryId()));
            inventoryEvents.reduceWith(() -> inventory, Inventory::incorporate);
        }
        return saveInventories(inventoryList);
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

}
