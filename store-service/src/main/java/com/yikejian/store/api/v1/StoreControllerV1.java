package com.yikejian.store.api.v1;

import com.yikejian.store.api.v1.dto.RequestStore;
import com.yikejian.store.api.v1.dto.StoreDto;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.exception.StoreServiceException;
import com.yikejian.store.service.StoreService;
import com.yikejian.store.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    @GetMapping("/stores")
    public ResponseEntity getStores(final @RequestParam(value = "params", required = false) String params) {
        // todo send log
        if (StringUtils.isBlank(params)) {
            return Optional.ofNullable(storeService.getAllEffectiveStores())
                    .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                    .orElseThrow(() -> new StoreServiceException("Not found any store."));
        }
        RequestStore requestStore;
        try {
            requestStore = JsonUtils.fromJson(URLDecoder.decode(params, "UTF-8"), RequestStore.class);
        } catch (UnsupportedEncodingException e) {
            throw new StoreServiceException(e.getLocalizedMessage());
        }
        requestStore.getStore().setDeleted(0);
        return Optional.ofNullable(storeService.getStores(requestStore))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not found any store."));
    }

}
