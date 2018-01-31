package com.yikejian.order.domain.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yikejian.order.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Entity
public class OrderExtra extends BaseEntity {

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
    /**
     * 附加信息
     */
//    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "order_id")
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
