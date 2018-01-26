package com.yikejian.store.api.v1.dto;

import com.yikejian.store.domain.store.Store;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestStore {

    private Store store;
    private Pagination pagination;
    private Sort sorter;

    public RequestStore() {
        setStore(new Store());
        setPagination(new Pagination());
        setSorter(new Sort());
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Sort getSorter() {
        return sorter;
    }

    public void setSorter(Sort sorter) {
        this.sorter = sorter;
    }
}
