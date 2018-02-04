package com.yikejian.store.api.v1.dto;

import com.yikejian.store.domain.store.Store;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestStoreOfClient {

    private Location location;
    private Pagination pagination;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
