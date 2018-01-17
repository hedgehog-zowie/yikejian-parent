package com.yikejian.order.domain.item;

import com.yikejian.order.domain.BaseEntity;
import com.yikejian.order.domain.order.Order;

import javax.persistence.*;

/**
 * @author jackalope
 * @Title: Item
 * @Package com.yikejian.order.domain.item
 * @Description: TODO
 * @date 2018/1/17 1:14
 */
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    private Long itemId;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 订单
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;
    /**
     * 实际体验产品的人
     */
    private String experiencer;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
}
