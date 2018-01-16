package com.yikejian.store.api.vi;

import com.yikejian.store.api.vi.dto.StoreDto;
import com.yikejian.store.api.vi.dto.RequestStoreDto;
import com.yikejian.store.exception.StoreServiceException;
import com.yikejian.store.service.StoreService;
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
 * <code>StoreControllerV1</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:57
 */
@RestController
public class StoreControllerV1 {

    private StoreService storeService;

    @Autowired
    public StoreControllerV1(StoreService storeService) {
        this.storeService = storeService;
    }

    @RequestMapping(value = "/store/{store_id}", method = RequestMethod.GET)
    public ResponseEntity getStores(final @PathVariable(value = "store_id") Long storeId) {
        // todo send log
        return Optional.ofNullable(storeService.getStoreById(storeId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not found store."));
    }

    @PostMapping("/store")
    public ResponseEntity addStore(final StoreDto storeDto) {
        // todo send log
        return Optional.ofNullable(storeService.saveStore(storeDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not found store."));
    }

    @PutMapping("/store")
    public ResponseEntity updateStore(final StoreDto storeDto) {
        // todo send log
        return Optional.ofNullable(storeService.saveStore(storeDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not found store."));
    }

    @GetMapping("/stores")
    public ResponseEntity getStores(final RequestStoreDto requestStoreDto) {
        // todo send log
        return Optional.ofNullable(storeService.getStores(requestStoreDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not found any store."));
    }

}
