package com.yikejian.order.domain.order;

import javax.persistence.Entity;

/**
 * @author jackalope
 * @Title: OrderEvent
 * @Package com.yikejian.order.domain.order
 * @Description: TODO
 * @date 2018/1/19 22:55
 */
@Entity
public class OrderEvent {

    private String orderEventId;
    private OrderEventType type;
    private String orderId;

}
