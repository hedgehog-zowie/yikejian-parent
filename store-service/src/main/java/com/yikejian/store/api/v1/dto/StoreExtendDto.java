package com.yikejian.store.api.v1.dto;

import com.yikejian.store.domain.store.StoreProduct;

import java.util.List;

/**
 * <code>StoreDeviceDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/18 13:34
 */
public class StoreExtendDto {

    /**
     * 店铺信息
     */
    private StoreDto store;
    /**
     * 店铺拥有的产品
     */
    private List<StoreProductDto> productList;

    public StoreExtendDto() {
    }

    public StoreExtendDto(StoreDto store, List<StoreProductDto> productList) {
        this.store = store;
        this.productList = productList;
    }

    public StoreDto getStore() {
        return store;
    }

    public void setStore(StoreDto store) {
        this.store = store;
    }

    public List<StoreProductDto> getProductList() {
        return productList;
    }

    public void setProductList(List<StoreProductDto> productList) {
        this.productList = productList;
    }
}
