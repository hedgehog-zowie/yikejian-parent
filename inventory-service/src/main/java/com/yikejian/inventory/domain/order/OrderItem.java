package com.yikejian.inventory.domain.order;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author jackalope
 * @Title: Item
 * @Package com.yikejian.order.domain.item
 * @Description: TODO
 * @date 2018/1/17 1:14
 */
public class OrderItem {

    private Long orderItemId;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 预约的时间
     */
    private String bookedTime;
    /**
     * 体验产品的用户
     */
    private String experiencer;
    /**
     * 订单
     */
    private Order order;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 服务状态
     */
    private OrderItemStatus orderItemStatus;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(String bookedTime) {
        this.bookedTime = bookedTime;
    }

    public String getExperiencer() {
        return experiencer;
    }

    public void setExperiencer(String experiencer) {
        this.experiencer = experiencer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public OrderItemStatus getOrderItemStatus() {
        return orderItemStatus;
    }

    public void setOrderItemStatus(OrderItemStatus orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }
}
