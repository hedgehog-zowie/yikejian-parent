package com.yikejian.store.api.v1;

import com.yikejian.store.api.v1.dto.RequestStore;
import com.yikejian.store.api.v1.dto.RequestStoreOfClient;
import com.yikejian.store.api.v1.dto.StoreDto;
import com.yikejian.store.exception.StoreServiceException;
import com.yikejian.store.service.StoreService;
import com.yikejian.store.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
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
@RequestMapping("/v1")
public class StoreControllerV1 {

    private StoreService storeService;

    @Autowired
    public StoreControllerV1(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/store")
    public ResponseEntity addStore(final @RequestBody StoreDto storeDto) {
        // todo send log
        storeDto.setStoreId(null);
        return Optional.ofNullable(storeService.saveStore(storeDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not save store."));
    }

    @PutMapping("/store")
    public ResponseEntity updateStore(final @RequestBody StoreDto storeDto) {
        // todo send log
        return Optional.ofNullable(storeService.saveStore(storeDto))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not found store."));
    }

    @RequestMapping(value = "/store/{store_id}", method = RequestMethod.GET)
    public ResponseEntity getStores(final @PathVariable(value = "store_id") Long storeId) {
        // todo send log
        return Optional.ofNullable(storeService.getStoreById(storeId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not found store."));
    }

    @RequestMapping(value = "/store/{store_id}/days", method = RequestMethod.GET)
    public ResponseEntity getBookableDays(final @PathVariable(value = "store_id") Long storeId) {
        // todo send log
        return Optional.ofNullable(storeService.getBookableDaysByStoreId(storeId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not found bookable days."));
    }

    @GetMapping("/stores")
    public ResponseEntity getStores(
            Principal principal,
            final @RequestParam(value = "params", required = false) String params) {
        // todo send log
        if (StringUtils.isBlank(params)) {
            return Optional.ofNullable(storeService.getAllEffectiveStores(principal))
                    .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                    .orElseThrow(() -> new StoreServiceException("Not found any store."));
        }
        RequestStoreOfClient requestStoreOfClient;
        RequestStore requestStore;
        try {
            requestStoreOfClient = JsonUtils.fromJson(URLDecoder.decode(params, "UTF-8"), RequestStoreOfClient.class);
            requestStore = JsonUtils.fromJson(URLDecoder.decode(params, "UTF-8"), RequestStore.class);
        } catch (UnsupportedEncodingException e) {
            throw new StoreServiceException(e.getLocalizedMessage());
        }

        if (requestStoreOfClient.getLocation() != null) {
            return Optional.ofNullable(storeService.getStores(requestStoreOfClient))
                    .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                    .orElseThrow(() -> new StoreServiceException("Not found any store."));
        } else {
            requestStore.getStore().setDeleted(0);
            return Optional.ofNullable(storeService.getStores(requestStore))
                    .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                    .orElseThrow(() -> new StoreServiceException("Not found any store."));
        }
    }

}
