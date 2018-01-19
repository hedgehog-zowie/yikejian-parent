package com.yikejian.order.api.v1.dto;

import com.yikejian.order.domain.order.Order;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestOrder {

    private Order order;
    private Pagination pagination;
    private Sort sort;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}