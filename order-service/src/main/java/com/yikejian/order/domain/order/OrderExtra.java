package com.yikejian.order.domain.order;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
     * 订单编号
     */
    private Long orderId;
    /**
     * 饮料
     */
    private String drink;
    /**
     * 备注
     */
    private String remark;

    public Long getExtraId() {
        return extraId;
    }

    public void setExtraId(Long extraId) {
        this.extraId = extraId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
}
