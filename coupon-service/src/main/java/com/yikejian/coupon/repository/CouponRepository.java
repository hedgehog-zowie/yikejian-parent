package com.yikejian.coupon.repository;

import com.yikejian.coupon.domain.coupon.Coupon;
import com.yikejian.coupon.domain.item.CouponItem;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>CouponRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface CouponRepository extends PagingAndSortingRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {

    Coupon findByCouponId(Long couponId);

    Coupon findByEffectiveAndDeleted(Integer effective, Integer deleted);

}
