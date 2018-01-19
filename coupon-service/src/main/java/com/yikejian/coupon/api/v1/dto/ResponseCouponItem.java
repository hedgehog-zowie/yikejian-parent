package com.yikejian.coupon.api.v1.dto;

import com.yikejian.coupon.domain.item.CouponItem;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseCouponItem {

    private List<CouponItem> couponItemList;
    private Pagination pagination;

    public ResponseCouponItem(List<CouponItem> couponItemList) {
        this.couponItemList = couponItemList;
    }

    public ResponseCouponItem(List<CouponItem> couponItemList, Pagination pagination) {
        this.couponItemList = couponItemList;
        this.pagination = pagination;
    }

    public List<CouponItem> getCouponItemList() {
        return couponItemList;
    }

    public void setCouponItemList(List<CouponItem> couponItemList) {
        this.couponItemList = couponItemList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
