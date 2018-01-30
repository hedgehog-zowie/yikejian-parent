package com.yikejian.gift.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.gift.api.v1.dto.Pagination;
import com.yikejian.gift.api.v1.dto.RequestGiftItem;
import com.yikejian.gift.api.v1.dto.ResponseGiftItem;
import com.yikejian.gift.domain.customer.Customer;
import com.yikejian.gift.domain.gift.Gift;
import com.yikejian.gift.domain.item.GiftItem;
import com.yikejian.gift.domain.item.GiftItemStatus;
import com.yikejian.gift.repository.GiftItemRepository;
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
public class GiftItemService {

    @Value("${yikejian.customer.api.url}")
    private String customerApi;

    private GiftItemRepository giftItemRepository;
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public GiftItemService(GiftItemRepository giftItemRepository,
                           OAuth2RestTemplate oAuth2RestTemplate) {
        this.giftItemRepository = giftItemRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @HystrixCommand
    public GiftItem saveGiftItem(GiftItem giftItem) {
        return giftItemRepository.save(giftItem);
    }

    @HystrixCommand
    public List<GiftItem> saveGiftItems(List<GiftItem> giftItemList) {
        return (List<GiftItem>) giftItemRepository.save(giftItemList);
    }

    @HystrixCommand
    public GiftItem getGiftItemById(Long giftItemId) {
        GiftItem giftItem = giftItemRepository.findByGiftItemId(giftItemId);
        Long customerId = giftItem.getCustomerId();
        Customer customer = oAuth2RestTemplate.getForObject(customerApi + "/" + customerId, Customer.class);
        giftItem.setCustomerName(customer.getCustomerName());
        return giftItem;
    }

    @HystrixCommand
    public ResponseGiftItem getAll() {
        return new ResponseGiftItem((List<GiftItem>) giftItemRepository.findAll());
    }

    @HystrixCommand
    public ResponseGiftItem getGiftItems(RequestGiftItem requestGiftItem) {
        Pagination pagination;
        if (requestGiftItem != null && requestGiftItem.getPagination() != null) {
            pagination = requestGiftItem.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestGiftItem != null && requestGiftItem.getSort() != null) {
            sort = new Sort(requestGiftItem.getSort().getOrder(), requestGiftItem.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent(),
                pagination.getPageSize(),
                sort);
        Page<GiftItem> page = giftItemRepository.findAll(couponSpec(requestGiftItem.getGiftItem()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());

        for (GiftItem giftItem : page.getContent()) {
            Long customerId = giftItem.getCustomerId();
            Customer customer = oAuth2RestTemplate.getForObject(customerApi + "/" + customerId, Customer.class);
            giftItem.setCustomerName(customer.getCustomerName());
        }
        return new ResponseGiftItem(page.getContent(), pagination);
    }

    private Specification<GiftItem> couponSpec(final GiftItem giftItem) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (giftItem != null) {
                if (StringUtils.isNotBlank(giftItem.getItemCode())) {
                    predicateList.add(cb.like(root.get("itemCode").as(String.class), "%" + giftItem.getItemCode() + "%"));
                }
                if (giftItem.getCustomerId() != null) {
                    predicateList.add(cb.equal(root.get("customerId").as(Integer.class), giftItem.getCustomerId()));
                }
                if (giftItem.getStatus() != null) {
                    predicateList.add(cb.equal(root.get("status").as(GiftItemStatus.class), giftItem.getStatus()));
                }
                if (giftItem.getGift() != null && giftItem.getGift().getGiftId() != null) {
                    Join<GiftItem, Gift> couponJoin = root.join(root.getModel().getSingularAttribute("gift", Gift.class), JoinType.LEFT);
                    predicateList.add(cb.equal(couponJoin.get("giftId").as(Long.class), giftItem.getGift().getGiftId()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
