package com.yikejian.order.api.v1.dto;

import com.yikejian.order.domain.order.Order;
import com.yikejian.order.domain.order.OrderItem;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestOrderItem {

    private OrderItem orderItem;
    private Pagination pagination;
    private Sort sorter;
    private Boolean running;

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
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

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }
}
