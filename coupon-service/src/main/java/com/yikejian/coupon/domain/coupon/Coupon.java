package com.yikejian.coupon.domain.coupon;

import com.yikejian.coupon.api.v1.dto.CouponDto;
import com.yikejian.coupon.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * <code>Coupon</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue
    private Long couponId;
    private String couponName;
    /**
     * 开始时间戳
     */
    private Long startTimeStamp;
    /**
     * 结束时间戳
     */
    private Long endTimeStamp;

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

    public void fromCouponDto(CouponDto couponDto) {
        if (StringUtils.isNotBlank(couponDto.getCouponName())) {
            setCouponName(couponDto.getCouponName());
        }
        if (couponDto.getStartTime() != null) {
            setDeleted(couponDto.getDeleted());
        }
        if (couponDto.getEndTime() != null) {
            setDeleted(couponDto.getDeleted());
        }
        if (couponDto.getEffective() != null) {
            setEffective(couponDto.getEffective());
        }
        if (couponDto.getDeleted() != null) {
            setDeleted(couponDto.getDeleted());
        }
    }

    public CouponDto toCouponDto() {
        CouponDto couponDto = new CouponDto();
        couponDto.setCouponId(getCouponId());
        couponDto.setCouponName(getCouponName());
        couponDto.setEffective(getEffective());
        couponDto.setLastModifiedBy(getLastModifiedBy());
        couponDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return couponDto;
    }

}
