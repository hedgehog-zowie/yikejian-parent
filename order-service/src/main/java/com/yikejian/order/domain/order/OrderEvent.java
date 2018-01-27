package com.yikejian.order.domain.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author jackalope
 * @Title: OrderEvent
 * @Package com.yikejian.order.domain.order
 * @Description: TODO
 * @date 2018/1/19 22:55
 */
@Entity
public class OrderEvent {

    @Id
    @GeneratedValue
    private String orderEventId;
    /**
     * 事件类型
     */
    private OrderEventType orderEventType;
    /**
     * 订单号
     */
    private String orderId;

    public String getOrderEventId() {
        return orderEventId;
    }

    public void setOrderEventId(String orderEventId) {
        this.orderEventId = orderEventId;
    }

    public OrderEventType getOrderEventType() {
        return orderEventType;
    }

    public void setOrderEventType(OrderEventType orderEventType) {
        this.orderEventType = orderEventType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
