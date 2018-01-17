package com.yikejian.coupon.domain.item;

import com.yikejian.coupon.domain.coupon.Coupon;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * <code>CouponItem</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/17 9:58
 */
@Entity
public class CouponItem {

    @Id
    @GeneratedValue
    private Long itemId;
    /**
     * 优惠券编码
     */
    private String itemCode;
    /**
     * 优惠券
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    /**
     * 客户
     */
    private Long customerId;
    /**
     * 状态
     */
    private CouponItemStatus status;

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

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public CouponItemStatus getStatus() {
        return status;
    }

    public void setStatus(CouponItemStatus status) {
        this.status = status;
    }
}
