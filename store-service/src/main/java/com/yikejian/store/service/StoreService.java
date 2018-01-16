package com.yikejian.store.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.store.api.vi.dto.Pagination;
import com.yikejian.store.api.vi.dto.StoreDto;
import com.yikejian.store.api.vi.dto.RequestStoreDto;
import com.yikejian.store.api.vi.dto.ResponseStoreDto;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.repository.StoreRepository;
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
 * <code>StoreService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Service
public class StoreService {

    private StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @HystrixCommand
    public Store saveStore(StoreDto storeDto) {
        Store store;
        if (storeDto.getStoreId() != null) {
            store = storeRepository.findByStoreId(storeDto.getStoreId());
        } else {
            store = new Store();
        }
        store.fromStoreDto(storeDto);
        return storeRepository.save(store);
    }

    @HystrixCommand
    public List<Store> saveStores(List<StoreDto> storeDtoList) {
        List<Store> storeList = Lists.newArrayList();
        for (StoreDto storeDto : storeDtoList) {
            Store store;
            if (storeDto.getStoreId() != null) {
                store = storeRepository.findByStoreId(storeDto.getStoreId());
            } else {
                store = new Store();
            }
            store.fromStoreDto(storeDto);
            storeList.add(store);
        }
        return (List<Store>) storeRepository.save(storeList);
    }

    @HystrixCommand
    public StoreDto getStoreById(Long storeId) {
        Store store = storeRepository.findByStoreId(storeId);
        return store.toStoreDto();
    }

    @HystrixCommand
    public ResponseStoreDto getAll() {
        List<Store> storeList = (List<Store>) storeRepository.findAll();
        List<StoreDto> storeDtoList = Lists.newArrayList(
                storeList.stream().map(Store::toStoreDto).collect(Collectors.toList())
        );
        return new ResponseStoreDto(storeDtoList);
    }

    @HystrixCommand
    public ResponseStoreDto getStores(RequestStoreDto requestStoreDto) {
        Pagination pagination;
        if (requestStoreDto != null && requestStoreDto.getPagination() != null) {
            pagination = requestStoreDto.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestStoreDto != null && requestStoreDto.getSort() != null) {
            sort = new Sort(requestStoreDto.getSort().getDirection(), requestStoreDto.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<Store> page = storeRepository.findAll(storeSpec(requestStoreDto.getStore()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());
        List<StoreDto> storeDtoList = Lists.newArrayList(page.getContent().stream().
                map(Store::toStoreDto).collect(Collectors.toList()));
        return new ResponseStoreDto(storeDtoList, pagination);
    }

    private Specification<Store> storeSpec(final StoreDto storeDto) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (storeDto != null) {
                if (StringUtils.isNotBlank(storeDto.getStoreName())) {
                    predicateList.add(cb.like(root.get("storeName").as(String.class), "%" + storeDto.getStoreName() + "%"));
                }
                if (storeDto.getStartTime() != null) {
                    predicateList.add(cb.equal(root.get("startTime").as(Integer.class), storeDto.getStartTime()));
                }
                if (storeDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("endTime").as(Integer.class), storeDto.getEndTime()));
                }
                if (storeDto.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), storeDto.getEffective()));
                }
                if (storeDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), storeDto.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
