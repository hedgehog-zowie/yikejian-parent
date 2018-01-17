package com.yikejian.inventory.api.v1;

import com.yikejian.inventory.api.v1.dto.InventoryDto;
import com.yikejian.inventory.api.v1.dto.RequestInventoryDto;
import com.yikejian.inventory.exception.InventoryServiceException;
import com.yikejian.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/inventory/{inventory_id}", method = RequestMethod.GET)
    public ResponseEntity getInventorys(final @PathVariable(value = "inventory_id") Long inventoryId) {
        // todo send log
        return Optional.ofNullable(inventoryService.getInventoryById(inventoryId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found inventory."));
    }

    @PostMapping("/inventory")
    public ResponseEntity addInventory(final InventoryDto inventoryDto) {
        // todo send log
        return Optional.ofNullable(inventoryService.saveInventory(inventoryDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found inventory."));
    }

    @PutMapping("/inventory")
    public ResponseEntity updateInventory(final InventoryDto inventoryDto) {
        // todo send log
        return Optional.ofNullable(inventoryService.saveInventory(inventoryDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found inventory."));
    }

    @GetMapping("/inventorys")
    public ResponseEntity getInventorys(final RequestInventoryDto requestInventoryDto) {
        // todo send log
        return Optional.ofNullable(inventoryService.getInventorys(requestInventoryDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new InventoryServiceException("Not found any inventory."));
    }

}
