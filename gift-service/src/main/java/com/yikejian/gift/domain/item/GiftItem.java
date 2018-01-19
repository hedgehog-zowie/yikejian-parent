package com.yikejian.gift.domain.item;

import com.yikejian.gift.domain.gift.Gift;

import javax.persistence.*;

/**
 * @author jackalope
 * @Title: GiftItem
 * @Package com.yikejian.gift.domain.item
 * @Description: TODO
 * @date 2018/1/20 1:22
 */
@Entity
public class GiftItem {

    @Id
    @GeneratedValue
    private Long itemId;
    /**
     * 礼品卡编码
     */
    private String itemCode;
    /**
     * 礼品卡
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gift_id")
    private Gift gift;
    /**
     * 客户
     */
    private Long customerId;
    /**
     * 客户名称
     */
    @Transient
    private String customerName;
    /**
     * 状态
     */
    private GiftItemStatus status;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
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

    public GiftItemStatus getStatus() {
        return status;
    }

    public void setStatus(GiftItemStatus status) {
        this.status = status;
    }
}
