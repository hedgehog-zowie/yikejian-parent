package com.yikejian.inventory.api.v1;

import com.yikejian.inventory.domain.inventory.Inventory;
import com.yikejian.inventory.domain.inventory.InventoryEvent;
import com.yikejian.inventory.domain.order.Order;
import com.yikejian.inventory.domain.store.Store;
import com.yikejian.inventory.exception.InventoryServiceException;
import com.yikejian.inventory.service.InventoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
@RequestMapping(path = "/v1")
public class InventoryControllerV1 {

    private InventoryService inventoryService;

    @Autowired
    public InventoryControllerV1(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PutMapping("/inventories")
    public ResponseEntity updateInventories(final @RequestBody List<Inventory> inventoryList) {
        // todo send log
        return Optional.ofNullable(inventoryService.saveInventories(inventoryList))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found inventory."));
    }

    @PutMapping("/inventory")
    public ResponseEntity updateInventory(final @RequestBody Inventory inventory) {
        // todo send log
        return Optional.ofNullable(inventoryService.saveInventory(inventory))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found inventory."));
    }

    @GetMapping("/inventories")
    public ResponseEntity getInventories(final @RequestBody Inventory inventory) {
        // todo send log
        return Optional.ofNullable(inventoryService.getInventories(inventory))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found any inventory."));
    }

    @Deprecated
    @RequestMapping(value = "/inventory/{inventory_id}/event", method = RequestMethod.POST)
    public ResponseEntity addInventoryEvent(
            @RequestBody InventoryEvent inventoryEvent,
            @PathVariable("inventory_id") String inventoryId) {
//        if(!Objects.equals(inventoryId, inventoryEvent.getInventoryId())){
//            throw new InventoryServiceException("params error.");
//        }
        // todo send log
        return Optional.ofNullable(inventoryService.addInventoryEvent(inventoryEvent))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Inventory event could not be applied to inventory."));
    }

    @RequestMapping(value = "/inventories/{store_id}/{product_id}", method = RequestMethod.GET)
    public ResponseEntity getStoreProductInventory(final @PathVariable(value = "store_id") Long storeId,
                                                   final @PathVariable(value = "product_id") Long productId,
                                                   final @RequestParam(value = "day", required = false) String day) {
        // TODO: 2018/1/22
        Inventory inventory = new Inventory();
        inventory.setStoreId(storeId);
        inventory.setProductId(productId);
        if (StringUtils.isNotBlank(day)) {
            inventory.setDay(day);
        }
        return Optional.ofNullable(inventoryService.getInventories(inventory))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found any inventory."));
    }

    @RequestMapping(value = "/inventories/store/{store_id}/product/{product_id}/items", method = RequestMethod.GET)
    public ResponseEntity getStoreProductInventoryItems(
            final @PathVariable(value = "store_id") Long storeId,
            final @PathVariable(value = "product_id") Long productId,
            final @RequestParam(value = "day", required = false) String day) {
        // TODO: 2018/1/22
        Inventory inventory = new Inventory();
        inventory.setStoreId(storeId);
        inventory.setProductId(productId);
        if (StringUtils.isNotBlank(day)) {
            inventory.setDay(day);
        }
        return Optional.ofNullable(inventoryService.getInventoryWithItems(inventory))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found any inventory."));
    }

    @RequestMapping(value = "/inventory/store", method = RequestMethod.POST)
    public ResponseEntity initStoreInventory(final @RequestBody Store store) {
        // TODO: 2018/1/22
        return Optional.ofNullable(inventoryService.initInventoryOfStore(store))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found any inventory."));
    }

    @RequestMapping(value = "/inventory/store/{day}", method = RequestMethod.POST)
    public ResponseEntity initStoreInventory(final @RequestBody Store store,
                                             final @PathVariable(value = "day") String day) {
        // TODO: 2018/1/22
        return Optional.ofNullable(inventoryService.initInventoryOfStore(store, day))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found any inventory."));
    }

    @RequestMapping(value = "/inventory/order", method = RequestMethod.POST)
    public ResponseEntity changeInventory(final @RequestBody Order order) {
        // TODO: 2018/1/22
        return Optional.ofNullable(inventoryService.changeInventoryOnOrderChange(order))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not change any inventory."));
    }

}
