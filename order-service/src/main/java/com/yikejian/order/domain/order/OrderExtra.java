package com.yikejian.order.domain.order;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author jackalope
 * @Title: OrderExtra
 * @Package com.yikejian.order.domain.order
 * @Description: TODO
 * @date 2018/1/17 1:28
 */
public class OrderExtra {

    @Id
    @GeneratedValue
    private Long extraId;
    /**
     * 饮料
     */
    private String drink;
    /**
     * 备注
     */
    private String remark;
    /**
     * 订单
     */
    @OneToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    public Long getExtraId() {
        return extraId;
    }

    public void setExtraId(Long extraId) {
        this.extraId = extraId;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
