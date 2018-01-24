package com.yikejian.store.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.store.api.v1.dto.Pagination;
import com.yikejian.store.api.v1.dto.RequestStore;
import com.yikejian.store.api.v1.dto.ResponseStore;
import com.yikejian.store.domain.product.Product;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.domain.store.StoreProduct;
import com.yikejian.store.repository.StoreProductRepository;
import com.yikejian.store.repository.StoreRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

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

    @Value("${yikejian.api.product.url}")
    private String productApiUrl;

    @Autowired
    public StoreService(StoreRepository storeRepository,
                        StoreProductRepository storeProductRepository,
                        OAuth2RestTemplate oAuth2RestTemplate) {
        this.storeRepository = storeRepository;
        this.storeProductRepository = storeProductRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @HystrixCommand
    public Store saveStore(Store store) {
        return storeRepository.save(transStore(store));
    }

    @HystrixCommand
    public List<Store> saveStores(List<Store> storeList) {
        List<Store> newStoreList = Lists.newArrayList();
        for (Store store : storeList) {
            newStoreList.add(transStore(store));
        }
        return (List<Store>) storeRepository.save(newStoreList);
    }

    private Store transStore(Store store) {
        Store newStore = store;
        if (store.getStoreId() != null) {
            Store oldStore = storeRepository.findByStoreId(store.getStoreId());
            newStore = oldStore.mergeOtherStore(store);
        }
        return newStore;
    }

    @HystrixCommand
    public Store getStoreById(Long storeId) {
        Store store = storeRepository.findByStoreId(storeId);
        setProductName(store);
        return storeRepository.findByStoreId(storeId);
    }

    @HystrixCommand
    public ResponseStore getAllStore() {
        return new ResponseStore((List<Store>) storeRepository.findAll());
    }

    @HystrixCommand
    public ResponseStore getStores(RequestStore requestStore) {
        Pagination pagination;
        if (requestStore != null && requestStore.getPagination() != null) {
            pagination = requestStore.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestStore != null && requestStore.getSort() != null) {
            sort = new Sort(requestStore.getSort().getDirection(), requestStore.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<Store> page = storeRepository.findAll(storeSpec(requestStore.getStore()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());

        for (Store store : page.getContent()) {
            setProductName(store);
        }

        return new ResponseStore(page.getContent(), pagination);
    }

    private Specification<Store> storeSpec(final Store store) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (store != null) {
                if (StringUtils.isNotBlank(store.getStoreName())) {
                    predicateList.add(cb.like(root.get("storeName").as(String.class), "%" + store.getStoreName() + "%"));
                }
                if (store.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), store.getEffective()));
                }
                if (store.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), store.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

    private void setProductName(Store store) {
        if (store.getStoreProductSet() == null) return;
        for (StoreProduct storeProduct : store.getStoreProductSet()) {
            Long productId = storeProduct.getProductId();
            Product product = oAuth2RestTemplate.getForObject(productApiUrl + "/" + productId, Product.class);
            storeProduct.setProductName(product.getProductName());
        }
    }

}
