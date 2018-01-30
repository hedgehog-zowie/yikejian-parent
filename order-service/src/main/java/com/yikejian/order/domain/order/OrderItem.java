package com.yikejian.order.domain.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yikejian.order.domain.BaseEntity;

import javax.persistence.*;
import java.util.Date;

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
    @Transient
    private String productName;
    /**
     * 服务时长
     */
    @Transient
    private Integer duration;
    /**
     * 服务进度
     */
    @Transient
    private Integer progress;
    /**
     * 实际体验产品的人
     */
    private String experiencer;
    /**
     * 预约的时间
     */
    private String bookedTime;
    /**
     * 订单号
     */
    private String orderCode;
    /**
     * 订单
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;
    /**
     * 服务状态
     */
//    @Transient
    private OrderItemStatus orderItemStatus;

    /**
     * 服务开始时间
     */
    private Date startAt;
    /**
     * 服务结束时间
     */
    private Date endAt;

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

    public String getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(String bookedTime) {
        this.bookedTime = bookedTime;
    }

    public OrderItemStatus getOrderItemStatus() {
        return orderItemStatus;
    }

    public void setOrderItemStatus(OrderItemStatus orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
