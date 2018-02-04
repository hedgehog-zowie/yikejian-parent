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
public class ResponseStoreOfClient {

    private List<StoreDto> list;
    private Pagination pagination;

    public ResponseStoreOfClient() {
    }

    public ResponseStoreOfClient(List<StoreDto> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<StoreDto> getList() {
        return list;
    }

    public void setList(List<StoreDto> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
