package com.yikejian.order.domain.order;

import com.yikejian.order.domain.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * <code>Order</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    private Long orderId;
    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 客户名称
     */
    @Transient
    private String customerName;
    /**
     * 店铺ID
     */
    private Long storeId;
    /**
     * 店铺名称
     */
    @Transient
    private String storeName;
    /**
     * 订单金额
     */
    private Double amount;
    /**
     * 实付金额
     */
    private Double actualAmount;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderItem> itemSet;
    /**
     * 附加信息
     */
    @OneToOne(targetEntity = OrderExtra.class)
    @JoinColumn(name = "extra_id", referencedColumnName = "extra_id")
    private OrderExtra orderExtra;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Set<OrderItem> getItemSet() {
        return itemSet;
    }

    public void setItemSet(Set<OrderItem> itemSet) {
        this.itemSet = itemSet;
    }

    public OrderExtra getOrderExtra() {
        return orderExtra;
    }

    public void setOrderExtra(OrderExtra orderExtra) {
        this.orderExtra = orderExtra;
    }
}