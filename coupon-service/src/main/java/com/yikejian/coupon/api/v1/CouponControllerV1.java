package com.yikejian.coupon.api.v1;

import com.yikejian.coupon.api.v1.dto.RequestCoupon;
import com.yikejian.coupon.domain.coupon.Coupon;
import com.yikejian.coupon.exception.CouponServiceException;
import com.yikejian.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * <code>CouponControllerV1</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:57
 */
@RestController
public class CouponControllerV1 {

    private CouponService couponService;

    @Autowired
    public CouponControllerV1(CouponService couponService) {
        this.couponService = couponService;
    }

    @RequestMapping(value = "/coupon/{coupon_id}", method = RequestMethod.GET)
    public ResponseEntity getCoupons(final @PathVariable(value = "coupon_id") Long couponId) {
        // todo send log
        return Optional.ofNullable(couponService.getCouponById(couponId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CouponServiceException("Not found coupon."));
    }

    @PostMapping("/coupon")
    public ResponseEntity addCoupon(final Coupon coupon) {
        // todo send log
        return Optional.ofNullable(couponService.saveCoupon(coupon))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CouponServiceException("Not found coupon."));
    }

    @PutMapping("/coupon")
    public ResponseEntity updateCoupon(final Coupon coupon) {
        // todo send log
        return Optional.ofNullable(couponService.saveCoupon(coupon))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CouponServiceException("Not found coupon."));
    }

    @GetMapping("/coupons")
    public ResponseEntity getCoupons(final RequestCoupon requestCoupon) {
        // todo send log
        return Optional.ofNullable(couponService.getCoupons(requestCoupon))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CouponServiceException("Not found any coupon."));
    }

}
