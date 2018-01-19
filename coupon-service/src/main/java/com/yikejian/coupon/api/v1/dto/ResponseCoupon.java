package com.yikejian.coupon.api.v1.dto;

import com.yikejian.coupon.domain.coupon.Coupon;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseCoupon {

    private List<Coupon> couponList;
    private Pagination pagination;

    public ResponseCoupon(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public ResponseCoupon(List<Coupon> couponList, Pagination pagination) {
        this.couponList = couponList;
        this.pagination = pagination;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
