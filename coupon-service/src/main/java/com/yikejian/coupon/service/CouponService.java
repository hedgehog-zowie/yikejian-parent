package com.yikejian.coupon.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.coupon.api.v1.dto.Pagination;
import com.yikejian.coupon.api.v1.dto.CouponDto;
import com.yikejian.coupon.api.v1.dto.RequestCouponDto;
import com.yikejian.coupon.api.v1.dto.ResponseCouponDto;
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
import java.util.stream.Collectors;

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
    public Coupon saveCoupon(CouponDto couponDto) {
        Coupon coupon;
        if (couponDto.getCouponId() != null) {
            coupon = couponRepository.findByCouponId(couponDto.getCouponId());
        } else {
            coupon = new Coupon();
        }
        coupon.fromCouponDto(couponDto);
        return couponRepository.save(coupon);
    }

    @HystrixCommand
    public List<Coupon> saveCoupons(List<CouponDto> couponDtoList) {
        List<Coupon> couponList = Lists.newArrayList();
        for (CouponDto couponDto : couponDtoList) {
            Coupon coupon;
            if (couponDto.getCouponId() != null) {
                coupon = couponRepository.findByCouponId(couponDto.getCouponId());
            } else {
                coupon = new Coupon();
            }
            coupon.fromCouponDto(couponDto);
            couponList.add(coupon);
        }
        return (List<Coupon>) couponRepository.save(couponList);
    }

    @HystrixCommand
    public CouponDto getCouponById(Long couponId) {
        Coupon coupon = couponRepository.findByCouponId(couponId);
        return coupon.toCouponDto();
    }

    @HystrixCommand
    public ResponseCouponDto getAll() {
        List<Coupon> couponList = (List<Coupon>) couponRepository.findAll();
        List<CouponDto> couponDtoList = Lists.newArrayList(
                couponList.stream().map(Coupon::toCouponDto).collect(Collectors.toList())
        );
        return new ResponseCouponDto(couponDtoList);
    }

    @HystrixCommand
    public ResponseCouponDto getCoupons(RequestCouponDto requestCouponDto) {
        Pagination pagination;
        if (requestCouponDto != null && requestCouponDto.getPagination() != null) {
            pagination = requestCouponDto.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestCouponDto != null && requestCouponDto.getSort() != null) {
            sort = new Sort(requestCouponDto.getSort().getDirection(), requestCouponDto.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<Coupon> page = couponRepository.findAll(couponSpec(requestCouponDto.getCoupon()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());
        List<CouponDto> couponDtoList = Lists.newArrayList(page.getContent().stream().
                map(Coupon::toCouponDto).collect(Collectors.toList()));
        return new ResponseCouponDto(couponDtoList, pagination);
    }

    private Specification<Coupon> couponSpec(final CouponDto couponDto) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (couponDto != null) {
                if (StringUtils.isNotBlank(couponDto.getCouponName())) {
                    predicateList.add(cb.like(root.get("couponName").as(String.class), "%" + couponDto.getCouponName() + "%"));
                }
                if (couponDto.getStartTime() != null) {
                    predicateList.add(cb.equal(root.get("startTime").as(Integer.class), couponDto.getStartTime()));
                }
                if (couponDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("endTime").as(Integer.class), couponDto.getEndTime()));
                }
                if (couponDto.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), couponDto.getEffective()));
                }
                if (couponDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), couponDto.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
