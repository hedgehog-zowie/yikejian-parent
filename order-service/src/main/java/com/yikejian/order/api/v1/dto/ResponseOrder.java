package com.yikejian.order.api.v1.dto;

import com.yikejian.order.domain.order.Order;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseOrder {

    private List<Order> list;
    private Pagination pagination;

    public ResponseOrder(List<Order> list) {
        this.list = list;
    }

    public ResponseOrder(List<Order> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<Order> getList() {
        return list;
    }

    public void setList(List<Order> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
