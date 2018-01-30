package com.yikejian.coupon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.coupon.api.v1.dto.Pagination;
import com.yikejian.coupon.api.v1.dto.RequestCoupon;
import com.yikejian.coupon.api.v1.dto.ResponseCoupon;
import com.yikejian.coupon.domain.coupon.Coupon;
import com.yikejian.coupon.repository.CouponRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>CouponService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Service
public class CouponService {

    private CouponRepository couponRepository;

    @Autowired
    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @HystrixCommand
    public Coupon saveCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @HystrixCommand
    public List<Coupon> saveCoupons(List<Coupon> couponList) {
        return (List<Coupon>) couponRepository.save(couponList);
    }

    @HystrixCommand
    public Coupon getCouponById(Long couponId) {
        return couponRepository.findByCouponId(couponId);
    }

    @HystrixCommand
    public ResponseCoupon getAll() {
        return new ResponseCoupon((List<Coupon>) couponRepository.findAll());
    }

    @HystrixCommand
    public ResponseCoupon getCoupons(RequestCoupon requestCoupon) {
        Pagination pagination;
        if (requestCoupon != null && requestCoupon.getPagination() != null) {
            pagination = requestCoupon.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestCoupon != null && requestCoupon.getSort() != null) {
            sort = new Sort(requestCoupon.getSort().getOrder(), requestCoupon.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent(),
                pagination.getPageSize(),
                sort);
        Page<Coupon> page = couponRepository.findAll(couponSpec(requestCoupon.getCoupon()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());

        return new ResponseCoupon(page.getContent(), pagination);
    }

    private Specification<Coupon> couponSpec(final Coupon coupon) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (coupon != null) {
                if (StringUtils.isNotBlank(coupon.getCouponName())) {
                    predicateList.add(cb.like(root.get("couponName").as(String.class), "%" + coupon.getCouponName() + "%"));
                }
                if (coupon.getStartTime() != null) {
                    predicateList.add(cb.equal(root.get("startTime").as(Integer.class), coupon.getStartTime()));
                }
                if (coupon.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("endTime").as(Integer.class), coupon.getEndTime()));
                }
                if (coupon.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), coupon.getEffective()));
                }
                if (coupon.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), coupon.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
