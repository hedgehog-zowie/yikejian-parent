package com.yikejian.store.api.v1.dto;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseStoreDto {

    private List<StoreDto> storeList;
    private Pagination pagination;

    public ResponseStoreDto(List<StoreDto> storeList) {
        this.storeList = storeList;
    }

    public ResponseStoreDto(List<StoreDto> storeList, Pagination pagination) {
        this.storeList = storeList;
        this.pagination = pagination;
    }

    public List<StoreDto> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StoreDto> storeList) {
        this.storeList = storeList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
