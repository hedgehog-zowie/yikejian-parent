package com.yikejian.store.api.v1;

import com.yikejian.store.domain.store.Store;
import com.yikejian.store.domain.store.StoreProduct;
import com.yikejian.store.exception.StoreServiceException;
import com.yikejian.store.service.StoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
public class StoreProductControllerV1 {

    private StoreProductService storeProductService;

    @Autowired
    public StoreProductControllerV1(StoreProductService storeProductService) {
        this.storeProductService = storeProductService;
    }

    @RequestMapping(value = "/store/{store_id}/product", method = RequestMethod.POST)
    public ResponseEntity addStoreProduct(
            final @PathVariable(value = "store_id") Long storeId,
            final @RequestBody StoreProduct storeProduct) {
        // todo send log
        storeProduct.setStoreProductId(null);
        storeProduct.setStore(new Store(storeId));
        return Optional.ofNullable(storeProductService.saveStoreProduct(storeProduct))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not save storeProduct."));
    }

    @RequestMapping(value = "/store/{store_id}/products", method = RequestMethod.POST)
    public ResponseEntity addStoreProducts(
            final @PathVariable(value = "store_id") Long storeId,
            final @RequestBody List<StoreProduct> storeProductList) {
        // todo send log
        Store store = new Store(storeId);
        for (StoreProduct storeProduct : storeProductList) {
            storeProduct.setStoreProductId(null);
            storeProduct.setStore(store);
        }
        return Optional.ofNullable(storeProductService.saveStoreProducts(storeProductList))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not save storeProduct."));
    }

    @RequestMapping(value = "/store/{store_id}/product", method = RequestMethod.PUT)
    public ResponseEntity updateStoreProduct(final @PathVariable(value = "store_id") Long storeId,
                                      final @RequestBody StoreProduct storeProduct) {
        // todo send log
        storeProduct.setStore(new Store(storeId));
        return Optional.ofNullable(storeProductService.saveStoreProduct(storeProduct))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not save storeProduct."));
    }

    @RequestMapping(value = "/store/{store_id}/products", method = RequestMethod.PUT)
    public ResponseEntity updateStoreProducts(final @PathVariable(value = "store_id") Long storeId,
                                              final @RequestBody List<StoreProduct> storeProductList) {
        // todo send log
        Store store = new Store(storeId);
        for (StoreProduct storeProduct : storeProductList) {
            storeProduct.setStoreProductId(null);
            storeProduct.setStore(store);
        }
        return Optional.ofNullable(storeProductService.saveStoreProducts(storeProductList))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not save storeProduct."));
    }

    @RequestMapping(value = "/store/{store_id}/products", method = RequestMethod.GET)
    public ResponseEntity getStoreProducts(final @PathVariable(value = "store_id") Long storeId) {
        // todo send log
        return Optional.ofNullable(storeProductService.getStoreProductByStoreId(storeId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not found store."));
    }

}
