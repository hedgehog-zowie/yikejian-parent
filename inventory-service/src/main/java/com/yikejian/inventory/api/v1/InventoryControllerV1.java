package com.yikejian.inventory.api.v1;

import com.yikejian.inventory.domain.inventory.Inventory;
import com.yikejian.inventory.domain.inventory.InventoryEvent;
import com.yikejian.inventory.domain.store.Store;
import com.yikejian.inventory.exception.InventoryServiceException;
import com.yikejian.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <code>InventoryControllerV1</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:57
 */
@RestController
public class InventoryControllerV1 {

    private InventoryService inventoryService;

    @Autowired
    public InventoryControllerV1(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PutMapping("/inventories")
    public ResponseEntity updateInventories(final List<Inventory> inventoryList) {
        // todo send log
        return Optional.ofNullable(inventoryService.saveInventories(inventoryList))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found inventory."));
    }

    @PutMapping("/inventory")
    public ResponseEntity updateInventory(final Inventory inventory) {
        // todo send log
        return Optional.ofNullable(inventoryService.saveInventory(inventory))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found inventory."));
    }

    @GetMapping("/inventories")
    public ResponseEntity getInventories(final Inventory inventory) {
        // todo send log
        return Optional.ofNullable(inventoryService.getInventories(inventory))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found any inventory."));
    }

    @RequestMapping(value = "/inventory/store", method = RequestMethod.POST)
    public ResponseEntity initStoreInventory(@RequestBody Store store){
        // TODO: 2018/1/22
        return Optional.ofNullable(inventoryService.initInventoryOfStore(store))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found any inventory."));
    }

    @RequestMapping(value = "/inventory/{inventory_id}/event", method = RequestMethod.POST)
    public ResponseEntity addInventoryEvent(
            @RequestBody InventoryEvent inventoryEvent,
            @PathVariable("inventory_id") String inventoryId){
        if(!Objects.equals(inventoryId, inventoryEvent.getInventoryId())){
            throw new InventoryServiceException("params error.");
        }
        // todo send log
        return Optional.ofNullable(inventoryService.addInventoryEvent(inventoryEvent))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Inventory event could not be applied to inventory."));
    }

}
