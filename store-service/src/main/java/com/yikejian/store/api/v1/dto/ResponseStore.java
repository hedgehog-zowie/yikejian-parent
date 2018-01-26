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

    private List<Store> list;
    private Pagination pagination;

    public ResponseStore() {
    }

    public ResponseStore(List<Store> list) {
        this.list = list;
    }

    public ResponseStore(List<Store> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<Store> getList() {
        return list;
    }

    public void setList(List<Store> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
