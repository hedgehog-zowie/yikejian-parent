package com.yikejian.coupon.api.vi.dto;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestCouponDto {

    private CouponDto coupon;
    private Pagination pagination;
    private Sort sort;

    public CouponDto getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponDto coupon) {
        this.coupon = coupon;
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
