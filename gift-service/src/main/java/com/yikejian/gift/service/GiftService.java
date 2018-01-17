package com.yikejian.gift.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.gift.api.v1.dto.Pagination;
import com.yikejian.gift.api.v1.dto.GiftDto;
import com.yikejian.gift.api.v1.dto.RequestGiftDto;
import com.yikejian.gift.api.v1.dto.ResponseGiftDto;
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
import java.util.stream.Collectors;

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
    public Gift saveGift(GiftDto giftDto) {
        Gift gift;
        if (giftDto.getGiftId() != null) {
            gift = giftRepository.findByGiftId(giftDto.getGiftId());
        } else {
            gift = new Gift();
        }
        gift.fromGiftDto(giftDto);
        return giftRepository.save(gift);
    }

    @HystrixCommand
    public List<Gift> saveGifts(List<GiftDto> giftDtoList) {
        List<Gift> giftList = Lists.newArrayList();
        for (GiftDto giftDto : giftDtoList) {
            Gift gift;
            if (giftDto.getGiftId() != null) {
                gift = giftRepository.findByGiftId(giftDto.getGiftId());
            } else {
                gift = new Gift();
            }
            gift.fromGiftDto(giftDto);
            giftList.add(gift);
        }
        return (List<Gift>) giftRepository.save(giftList);
    }

    @HystrixCommand
    public GiftDto getGiftById(Long giftId) {
        Gift gift = giftRepository.findByGiftId(giftId);
        return gift.toGiftDto();
    }

    @HystrixCommand
    public ResponseGiftDto getAll() {
        List<Gift> giftList = (List<Gift>) giftRepository.findAll();
        List<GiftDto> giftDtoList = Lists.newArrayList(
                giftList.stream().map(Gift::toGiftDto).collect(Collectors.toList())
        );
        return new ResponseGiftDto(giftDtoList);
    }

    @HystrixCommand
    public ResponseGiftDto getGifts(RequestGiftDto requestGiftDto) {
        Pagination pagination;
        if (requestGiftDto != null && requestGiftDto.getPagination() != null) {
            pagination = requestGiftDto.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestGiftDto != null && requestGiftDto.getSort() != null) {
            sort = new Sort(requestGiftDto.getSort().getDirection(), requestGiftDto.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<Gift> page = giftRepository.findAll(giftSpec(requestGiftDto.getGift()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());
        List<GiftDto> giftDtoList = Lists.newArrayList(page.getContent().stream().
                map(Gift::toGiftDto).collect(Collectors.toList()));
        return new ResponseGiftDto(giftDtoList, pagination);
    }

    private Specification<Gift> giftSpec(final GiftDto giftDto) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (giftDto != null) {
                if (StringUtils.isNotBlank(giftDto.getGiftName())) {
                    predicateList.add(cb.like(root.get("giftName").as(String.class), "%" + giftDto.getGiftName() + "%"));
                }
                if (giftDto.getStartTime() != null) {
                    predicateList.add(cb.equal(root.get("startTime").as(Integer.class), giftDto.getStartTime()));
                }
                if (giftDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("endTime").as(Integer.class), giftDto.getEndTime()));
                }
                if (giftDto.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), giftDto.getEffective()));
                }
                if (giftDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), giftDto.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
