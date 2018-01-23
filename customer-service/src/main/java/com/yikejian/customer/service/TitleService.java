package com.yikejian.customer.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.customer.api.v1.dto.Pagination;
import com.yikejian.customer.api.v1.dto.RequestTitle;
import com.yikejian.customer.api.v1.dto.ResponseTitle;
import com.yikejian.customer.domain.title.Title;
import com.yikejian.customer.repository.TitleRepository;
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
 * @author jackalope
 * @Title: TitleService
 * @Package com.yikejian.Title.service
 * @Description: TODO
 * @date 2018/1/14 10:36
 */
@Service
public class TitleService {

    private TitleRepository titleRepository;

    @Autowired
    public TitleService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @HystrixCommand
    public Title saveTitle(Title title) {
        return titleRepository.save(transform(title));
    }

    @HystrixCommand
    public List<Title> saveTitles(List<Title> titleList) {
        return (List<Title>) titleRepository.save(
                Lists.newArrayList(titleList.stream().
                        map(this::transform).collect(Collectors.toList()))
        );
    }

    private Title transform(Title title) {
        Title newTitle = title;
        if (title.getTitleId() != null) {
            Title oldTitle = titleRepository.findByTitleId(title.getTitleId());
            newTitle = oldTitle.mergeOther(title);
        }
        return newTitle;
    }

    @HystrixCommand
    public Title getTitleById(Long titleId) {
        return titleRepository.findByTitleId(titleId);
    }

    @HystrixCommand
    public ResponseTitle getAll() {
        return new ResponseTitle((List<Title>) titleRepository.findAll());
    }

    @HystrixCommand
    public ResponseTitle getTitles(RequestTitle requestTitle) {
        Pagination pagination;
        if (requestTitle != null && requestTitle.getPagination() != null) {
            pagination = requestTitle.getPagination();
        } else {
            pagination = new Pagination();
        }

        String filed = "lastModifiedAt";
        Sort.Direction direction = Sort.Direction.DESC;
        if (requestTitle != null && requestTitle.getSorter() != null) {
            if (requestTitle.getSorter().getField() != null) {
                filed = requestTitle.getSorter().getField();
            }
            if ("ascend".equals(requestTitle.getSorter().getOrder())) {
                direction = Sort.Direction.ASC;
            }
        }
        Sort sort = new Sort(direction, filed);

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent() - 1,
                pagination.getPageSize(),
                sort);
        Page<Title> page = titleRepository.findAll(titleSpec(requestTitle.getTitle()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());
        List<Title> titleList = page.getContent();
        return new ResponseTitle(titleList, pagination);
    }

    private Specification<Title> titleSpec(final Title title) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (title != null) {
                if (title.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), title.getDeleted()));
                }
                if (title.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), title.getEffective()));
                }
                if (StringUtils.isNotBlank(title.getTitleName())) {
                    predicateList.add(cb.like(root.get("titleName").as(String.class), "%" + title.getTitleName() + "%"));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
