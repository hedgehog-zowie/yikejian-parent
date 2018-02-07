package com.yikejian.inventory.api.v1.dto;

import com.yikejian.inventory.domain.order.OrderItemStatus;

/**
 * @author jackalope
 * @Title: OrderItemDto
 * @Package com.yikejian.inventory.api.v1.dto
 * @Description: TODO
 * @date 2018/2/7 23:41
 */
public class OrderItemDto {

    private String orderCode;
    private String experiencer;
    private OrderItemStatus orderItemStatus;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getExperiencer() {
        return experiencer;
    }

    public void setExperiencer(String experiencer) {
        this.experiencer = experiencer;
    }

    public OrderItemStatus getOrderItemStatus() {
        return orderItemStatus;
    }

    public void setOrderItemStatus(OrderItemStatus orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }
}
