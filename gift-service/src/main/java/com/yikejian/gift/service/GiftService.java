package com.yikejian.gift.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.gift.api.v1.dto.Pagination;
import com.yikejian.gift.api.v1.dto.RequestGift;
import com.yikejian.gift.api.v1.dto.ResponseGift;
import com.yikejian.gift.domain.gift.Gift;
import com.yikejian.gift.repository.GiftRepository;
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
 * <code>GiftService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Service
public class GiftService {

    private GiftRepository giftRepository;

    @Autowired
    public GiftService(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    @HystrixCommand
    public Gift saveGift(Gift gift) {
        return giftRepository.save(gift);
    }

    @HystrixCommand
    public List<Gift> saveGifts(List<Gift> giftList) {
        return (List<Gift>) giftRepository.save(giftList);
    }

    @HystrixCommand
    public Gift getGiftById(Long giftId) {
        return giftRepository.findByGiftId(giftId);
    }

    @HystrixCommand
    public ResponseGift getAll() {
        return new ResponseGift((List<Gift>) giftRepository.findAll());
    }

    @HystrixCommand
    public ResponseGift getGifts(RequestGift requestGift) {
        Pagination pagination;
        if (requestGift != null && requestGift.getPagination() != null) {
            pagination = requestGift.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestGift != null && requestGift.getSort() != null) {
            sort = new Sort(requestGift.getSort().getOrder(), requestGift.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent(),
                pagination.getPageSize(),
                sort);
        Page<Gift> page = giftRepository.findAll(giftSpec(requestGift.getGift()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());

        return new ResponseGift(page.getContent(), pagination);
    }

    private Specification<Gift> giftSpec(final Gift gift) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (gift != null) {
                if (StringUtils.isNotBlank(gift.getGiftName())) {
                    predicateList.add(cb.like(root.get("giftName").as(String.class), "%" + gift.getGiftName() + "%"));
                }
                if (gift.getStartTime() != null) {
                    predicateList.add(cb.equal(root.get("startTime").as(Integer.class), gift.getStartTime()));
                }
                if (gift.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("endTime").as(Integer.class), gift.getEndTime()));
                }
                if (gift.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), gift.getEffective()));
                }
                if (gift.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), gift.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
