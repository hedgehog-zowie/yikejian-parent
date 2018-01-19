package com.yikejian.coupon.domain.coupon;

import com.yikejian.coupon.domain.BaseEntity;
import com.yikejian.coupon.domain.item.CouponItem;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * <code>Coupon</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
@Entity
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue
    private Long couponId;
    /**
     * 优惠券名称
     */
    private String couponName;
    /**
     * 优惠券类型
     */
    private String couponType;
    /**
     * 数量
     */
    private Integer number;
    /**
     * 剩余数量
     */
    private Integer surplus;
    /**
     * 金额
     */
    private Double money;
    /**
     * 生效时间戳
     */
    private Long startTime;
    /**
     * 失效时间戳
     */
    private Long endTime;

    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
    private Set<CouponItem> itemSet;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSurplus() {
        return surplus;
    }

    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Set<CouponItem> getItemSet() {
        return itemSet;
    }

    public void setItemSet(Set<CouponItem> itemSet) {
        this.itemSet = itemSet;
    }
}
