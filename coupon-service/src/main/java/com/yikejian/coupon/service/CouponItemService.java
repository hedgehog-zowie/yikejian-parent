package com.yikejian.coupon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.coupon.api.v1.dto.Pagination;
import com.yikejian.coupon.api.v1.dto.RequestCouponItem;
import com.yikejian.coupon.api.v1.dto.ResponseCouponItem;
import com.yikejian.coupon.domain.coupon.Coupon;
import com.yikejian.coupon.domain.customer.Customer;
import com.yikejian.coupon.domain.item.CouponItem;
import com.yikejian.coupon.domain.item.CouponItemStatus;
import com.yikejian.coupon.repository.CouponItemRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
public class CouponItemService {

    @Value("${yikejian.customer.api.url}")
    private String customerApi;

    private CouponItemRepository couponItemRepository;
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public CouponItemService(CouponItemRepository couponItemRepository,
                             OAuth2RestTemplate oAuth2RestTemplate) {
        this.couponItemRepository = couponItemRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @HystrixCommand
    public CouponItem saveCouponItem(CouponItem couponItem) {
        return couponItemRepository.save(couponItem);
    }

    @HystrixCommand
    public List<CouponItem> saveCouponItems(List<CouponItem> couponItemList) {
        return (List<CouponItem>) couponItemRepository.save(couponItemList);
    }

    @HystrixCommand
    public CouponItem getCouponItemById(Long couponItemId) {
        CouponItem couponItem = couponItemRepository.findByCouponItemId(couponItemId);
        Long customerId = couponItem.getCustomerId();
        Customer customer = oAuth2RestTemplate.getForObject(customerApi + "/" + customerId, Customer.class);
        couponItem.setCustomerName(customer.getCustomerName());
        return couponItem;
    }

    @HystrixCommand
    public ResponseCouponItem getAll() {
        return new ResponseCouponItem((List<CouponItem>) couponItemRepository.findAll());
    }

    @HystrixCommand
    public ResponseCouponItem getCouponItems(RequestCouponItem requestCouponItem) {
        Pagination pagination;
        if (requestCouponItem != null && requestCouponItem.getPagination() != null) {
            pagination = requestCouponItem.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestCouponItem != null && requestCouponItem.getSort() != null) {
            sort = new Sort(requestCouponItem.getSort().getOrder(), requestCouponItem.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent(),
                pagination.getPageSize(),
                sort);
        Page<CouponItem> page = couponItemRepository.findAll(couponSpec(requestCouponItem.getCouponItem()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());

        for (CouponItem couponItem : page.getContent()) {
            Long customerId = couponItem.getCustomerId();
            Customer customer = oAuth2RestTemplate.getForObject(customerApi + "/" + customerId, Customer.class);
            couponItem.setCustomerName(customer.getCustomerName());
        }
        return new ResponseCouponItem(page.getContent(), pagination);
    }

    private Specification<CouponItem> couponSpec(final CouponItem couponItem) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (couponItem != null) {
                if (StringUtils.isNotBlank(couponItem.getItemCode())) {
                    predicateList.add(cb.like(root.get("itemCode").as(String.class), "%" + couponItem.getItemCode() + "%"));
                }
                if (couponItem.getCustomerId() != null) {
                    predicateList.add(cb.equal(root.get("customerId").as(Integer.class), couponItem.getCustomerId()));
                }
                if (couponItem.getStatus() != null) {
                    predicateList.add(cb.equal(root.get("status").as(CouponItemStatus.class), couponItem.getStatus()));
                }
                if (couponItem.getCoupon() != null && couponItem.getCoupon().getCouponId() != null) {
                    Join<CouponItem, Coupon> couponJoin = root.join(root.getModel().getSingularAttribute("coupon", Coupon.class), JoinType.LEFT);
                    predicateList.add(cb.equal(couponJoin.get("couponId").as(Long.class), couponItem.getCoupon().getCouponId()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
