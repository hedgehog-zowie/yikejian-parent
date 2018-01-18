package com.yikejian.store.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.store.api.v1.dto.Pagination;
import com.yikejian.store.api.v1.dto.ProductDto;
import com.yikejian.store.api.v1.dto.RequestStoreDto;
import com.yikejian.store.api.v1.dto.ResponseStoreDto;
import com.yikejian.store.api.v1.dto.StoreDto;
import com.yikejian.store.api.v1.dto.StoreExtendDto;
import com.yikejian.store.api.v1.dto.StoreProductDto;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.domain.store.StoreProduct;
import com.yikejian.store.exception.StoreServiceException;
import com.yikejian.store.repository.StoreProductRepository;
import com.yikejian.store.repository.StoreRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
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
    private StoreProductRepository storeProductRepository;
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public StoreService(StoreRepository storeRepository,
                        StoreProductRepository storeProductRepository,
                        OAuth2RestTemplate oAuth2RestTemplate) {
        this.storeRepository = storeRepository;
        this.storeProductRepository = storeProductRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @HystrixCommand
    public Store saveStore(StoreExtendDto storeExtendDto) {
        return storeRepository.save(storeExtendDtoToStore(storeExtendDto));
    }

    @HystrixCommand
    public List<Store> saveStores(List<StoreExtendDto> storeDtoList) {
        return (List<Store>) storeRepository.save(Lists.newArrayList(
                storeDtoList.stream().
                        map(this::storeExtendDtoToStore).collect(Collectors.toList())
        ));
    }

    @HystrixCommand
    public StoreExtendDto getStoreById(Long storeId) {
        return storeToStoreExtendDto(storeRepository.findByStoreId(storeId));
    }

    @HystrixCommand
    public ResponseStoreDto getAll() {
        List<Store> storeList = (List<Store>) storeRepository.findAll();
        List<StoreDto> storeDtoList = Lists.newArrayList(
                storeList.stream().map(this::storeToStoreDto).collect(Collectors.toList())
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
                map(this::storeToStoreDto).collect(Collectors.toList()));
        return new ResponseStoreDto(storeDtoList, pagination);
    }

    private Specification<Store> storeSpec(final StoreDto storeDto) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (storeDto != null) {
                if (StringUtils.isNotBlank(storeDto.getStoreName())) {
                    predicateList.add(cb.like(root.get("storeName").as(String.class), "%" + storeDto.getStoreName() + "%"));
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

    private StoreExtendDto storeToStoreExtendDto(Store store) {
        StoreDto storeDto = storeToStoreDto(store);
        List<StoreProductDto> storeProductDtoList = Lists.newArrayList(
                store.getStoreProductSet().stream().
                        map(this::storeProductToStoreProductDto).collect(Collectors.toList())
        );
        return new StoreExtendDto(storeDto, storeProductDtoList);
    }

    private StoreDto storeToStoreDto(Store store) {
        StoreDto storeDto = new StoreDto();
        storeDto.setStoreId(store.getStoreId());
        storeDto.setStoreName(store.getStoreName());
        storeDto.setAddress(store.getAddress());
        storeDto.setPhoneNumber(store.getPhoneNumber());
        storeDto.setStartTime(store.getStartTime());
        storeDto.setEndTime(store.getEndTime());
        storeDto.setTraffic(store.getTraffic());
        storeDto.setLongitude(store.getLongitude());
        storeDto.setLatitude(store.getLatitude());
        storeDto.setEffective(store.getEffective());
        storeDto.setDeleted(store.getDeleted());
        storeDto.setLastModifiedBy(store.getLastModifiedBy());
        storeDto.setLastModifiedAt(store.getLastModifiedAt() == null ? null : new Date(store.getLastModifiedAt()));
        return storeDto;
    }

    private Store storeExtendDtoToStore(StoreExtendDto storeExtendDto) {
        StoreDto storeDto = storeExtendDto.getStore();
        Store store;
        if (storeDto.getStoreId() != null) {
            store = storeRepository.findByStoreId(storeDto.getStoreId());
            if (store == null) {
                throw new StoreServiceException("未知的店铺");
            }
        } else {
            store = new Store();
        }
        if (StringUtils.isNotBlank(storeDto.getStoreName())) {
            store.setStoreName(storeDto.getStoreName());
        }
        if (StringUtils.isNotBlank(storeDto.getAddress())) {
            store.setAddress(storeDto.getAddress());
        }
        if (StringUtils.isNotBlank(storeDto.getPhoneNumber())) {
            store.setPhoneNumber(storeDto.getPhoneNumber());
        }
        if (storeDto.getStartTime() != null) {
            store.setStartTime(storeDto.getDeleted());
        }
        if (storeDto.getEndTime() != null) {
            store.setEndTime(storeDto.getDeleted());
        }
        if (storeDto.getUnitDuration() != null) {
            store.setUnitDuration(storeDto.getUnitDuration());
        }
        if (storeDto.getUnitTimes() != null) {
            store.setUnitTimes(storeDto.getUnitTimes());
        }
        if (StringUtils.isNotBlank(storeDto.getTraffic())) {
            store.setTraffic(storeDto.getTraffic());
        }
        if (storeDto.getLongitude() != null) {
            store.setLongitude(storeDto.getLongitude());
        }
        if (storeDto.getLatitude() != null) {
            store.setLatitude(storeDto.getLatitude());
        }
        if (storeDto.getEffective() != null) {
            store.setEffective(storeDto.getEffective());
        }
        if (storeDto.getDeleted() != null) {
            store.setDeleted(storeDto.getDeleted());
        }
        // set store product
        if (storeExtendDto.getProductList() != null && storeExtendDto.getProductList().size() > 0) {
            store.setStoreProductSet(Sets.newHashSet(
                    storeExtendDto.getProductList().stream().
                            map(this::storeProductDtoToStoreProduct).collect(Collectors.toList())
            ));
        }
        return store;
    }

    private StoreProductDto storeProductToStoreProductDto(StoreProduct storeProduct) {
        StoreProductDto storeProductDto = new StoreProductDto();
        storeProductDto.setId(storeProduct.getId());
        storeProductDto.setProductId(storeProduct.getProductId());
        storeProductDto.setEffective(storeProduct.getEffective());
        storeProductDto.setDeleted(storeProduct.getDeleted());
        storeProductDto.setLastModifiedBy(storeProduct.getLastModifiedBy());
        storeProductDto.setLastModifiedAt(storeProduct.getLastModifiedAt() == null ? null : new Date(storeProduct.getLastModifiedAt()));
        // TODO: 2018/1/18 fetch product from product service
        ProductDto productDto = oAuth2RestTemplate.getForObject("get product url", ProductDto.class);
        storeProductDto.setProductName(productDto.getProductName());
        return storeProductDto;
    }

    private StoreProduct storeProductDtoToStoreProduct(StoreProductDto storeProductDto) {
        StoreProduct storeProduct;
        if (storeProductDto.getStoreId() != null) {
            storeProduct = storeProductRepository.findOne(storeProductDto.getId());
            if (storeProduct == null) {
                throw new StoreServiceException("未找到产品: " + storeProductDto.getId());
            }
        } else {
            storeProduct = new StoreProduct();
        }
        if (storeProductDto.getProductId() == null) {
            throw new StoreServiceException("未设置产品ID");
        }
        storeProduct.setProductId(storeProductDto.getProductId());
        return storeProduct;
    }

}
