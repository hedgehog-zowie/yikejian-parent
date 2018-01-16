package com.yikejian.coupon.api.vi.dto;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseCouponDto {

    private List<CouponDto> couponList;
    private Pagination pagination;

    public ResponseCouponDto(List<CouponDto> couponList) {
        this.couponList = couponList;
    }

    public ResponseCouponDto(List<CouponDto> couponList, Pagination pagination) {
        this.couponList = couponList;
        this.pagination = pagination;
    }

    public List<CouponDto> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponDto> couponList) {
        this.couponList = couponList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
