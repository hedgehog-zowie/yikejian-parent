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
public class OrderItemEvent {

    @Id
    @GeneratedValue
    private String orderItemEventId;
    /**
     * 事件类型
     */
    private OrderItemEventType orderItemEventType;
    /**
     * 项目号
     */
    private String orderItemId;

    public String getOrderItemEventId() {
        return orderItemEventId;
    }

    public void setOrderItemEventId(String orderItemEventId) {
        this.orderItemEventId = orderItemEventId;
    }

    public OrderItemEventType getOrderItemEventType() {
        return orderItemEventType;
    }

    public void setOrderItemEventType(OrderItemEventType orderItemEventType) {
        this.orderItemEventType = orderItemEventType;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }
}
