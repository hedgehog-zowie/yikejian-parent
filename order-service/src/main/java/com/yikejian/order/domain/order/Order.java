package com.yikejian.order.domain.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yikejian.order.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

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
@Entity(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    private Long orderId;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 客户电话
     */
    private String mobileNumber;
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
     * 实际应付金额
     */
    private Double actualAmount;
    /**
     * 订单状态
     */
//    @Transient
    private OrderStatus orderStatus;
    /**
     * 项目
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems;

//    /**
//     * 附加信息
//     */
////    @JsonManagedReference
//    @OneToOne
//    @JoinColumn(name = "extra_id")
////    @PrimaryKeyJoinColumn
//    private OrderExtra orderExtra;

    public Order() {
    }

    public Order(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 订单事件
     */
//    @JsonIgnore
//    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
//    private Set<OrderEvent> eventSet;
    public Order mergeOther(Order other) {
        if (StringUtils.isNotBlank(other.getMobileNumber())) {
            setMobileNumber(other.getMobileNumber());
        }
        if (StringUtils.isNotBlank(other.getCustomerName())) {
            setCustomerName(other.getCustomerName());
        }
        if (other.getStoreId() != null) {
            setStoreId(other.getStoreId());
        }
        if (other.getAmount() != null) {
            setAmount(other.getAmount());
        }
        if (other.getActualAmount() != null) {
            setActualAmount(other.getActualAmount());
        }
        if (other.getOrderItems() != null) {
            setOrderItems(other.getOrderItems());
        }
        if (other.getEffective() != null) {
            setEffective(other.getEffective());
        }
        if (other.getDeleted() != null) {
            setDeleted(other.getDeleted());
        }
        return this;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    //    public OrderExtra getOrderExtra() {
//        return orderExtra;
//    }
//
//    public void setOrderExtra(OrderExtra orderExtra) {
//        this.orderExtra = orderExtra;
//    }

//    public Set<OrderEvent> getEventSet() {
//        return eventSet;
//    }
//
//    public void setEventSet(Set<OrderEvent> eventSet) {
//        this.eventSet = eventSet;
//    }
}