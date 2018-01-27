package com.yikejian.order.domain.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yikejian.order.domain.BaseEntity;

import javax.persistence.*;

/**
 * @author jackalope
 * @Title: Item
 * @Package com.yikejian.order.domain.item
 * @Description: TODO
 * @date 2018/1/17 1:14
 */
@Entity
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    private Long orderItemId;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 订单
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;
    /**
     * 实际体验产品的人
     */
    private String experiencer;
    /**
     * 预约的时间
     */
    private String dateTime;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getExperiencer() {
        return experiencer;
    }

    public void setExperiencer(String experiencer) {
        this.experiencer = experiencer;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public OrderItemStatus getOrderItemStatus() {
        return orderItemStatus;
    }

    public void setOrderItemStatus(OrderItemStatus orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }
}
