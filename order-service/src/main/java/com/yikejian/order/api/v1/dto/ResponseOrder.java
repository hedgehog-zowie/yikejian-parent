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

    private List<Order> orderList;
    private Pagination pagination;

    public ResponseOrder(List<Order> orderList) {
        this.orderList = orderList;
    }

    public ResponseOrder(List<Order> orderList, Pagination pagination) {
        this.orderList = orderList;
        this.pagination = pagination;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
