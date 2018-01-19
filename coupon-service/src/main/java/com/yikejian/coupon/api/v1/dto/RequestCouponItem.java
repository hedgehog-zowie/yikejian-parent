package com.yikejian.coupon.api.v1.dto;

import com.yikejian.coupon.domain.coupon.Coupon;
import com.yikejian.coupon.domain.item.CouponItem;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestCouponItem {

    private CouponItem couponItem;
    private Pagination pagination;
    private Sort sort;

    public CouponItem getCouponItem() {
        return couponItem;
    }

    public void setCouponItem(CouponItem couponItem) {
        this.couponItem = couponItem;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

}
