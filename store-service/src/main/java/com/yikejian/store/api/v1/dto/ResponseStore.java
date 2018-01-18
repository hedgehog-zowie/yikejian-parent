package com.yikejian.store.api.v1.dto;

import com.yikejian.store.domain.store.Store;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseStore {

    private List<Store> storeList;
    private Pagination pagination;

    public ResponseStore(List<Store> storeList) {
        this.storeList = storeList;
    }

    public ResponseStore(List<Store> storeList, Pagination pagination) {
        this.storeList = storeList;
        this.pagination = pagination;
    }

    public List<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
