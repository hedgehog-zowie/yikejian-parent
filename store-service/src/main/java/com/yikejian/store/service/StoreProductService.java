package com.yikejian.store.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.store.domain.product.Product;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.domain.store.StoreProduct;
import com.yikejian.store.repository.StoreProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jackalope
 * @Title: StoreProductService
 * @Package com.yikejian.store.service
 * @Description: TODO
 * @date 2018/1/18 23:23
 */
@Service
public class StoreProductService {

    private StoreProductRepository storeProductRepository;
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Value("${yikejian.api.product.url}")
    private String productApiUrl;

    @Autowired
    public StoreProductService(StoreProductRepository storeProductRepository,
                               OAuth2RestTemplate oAuth2RestTemplate) {
        this.storeProductRepository = storeProductRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @HystrixCommand
    public StoreProduct saveStoreProduct(StoreProduct storeProduct) {
        return storeProductRepository.save(transStoreProduct(storeProduct));
    }

    @HystrixCommand
    public List<StoreProduct> saveStoreProducts(List<StoreProduct> storeProductList) {
        List<StoreProduct> newStoreProductList = Lists.newArrayList();
        for (StoreProduct storeProduct : storeProductList) {
            newStoreProductList.add(transStoreProduct(storeProduct));
        }
        return (List<StoreProduct>) storeProductRepository.save(newStoreProductList);
    }

    private StoreProduct transStoreProduct(StoreProduct storeProduct) {
        StoreProduct newStoreProduct = storeProduct;
        if (storeProduct.getStoreProductId() != null) {
            StoreProduct oldStoreProduct = storeProductRepository.findByStoreProductId(storeProduct.getStoreProductId());
            newStoreProduct = oldStoreProduct.mergeOther(storeProduct);
        }
        return newStoreProduct;
    }

    @HystrixCommand
    public StoreProduct getStoreProductById(Long storeId) {
        return storeProductRepository.findByStoreProductId(storeId);
    }

    @HystrixCommand
    public List<StoreProduct> getStoreProductByStoreId(Long storeId) {
        StoreProduct queryStoreProduct = new StoreProduct();
        Store store = new Store(storeId);
        queryStoreProduct.setStore(store);
        queryStoreProduct.setDeleted(0);
        queryStoreProduct.setEffective(1);
        List<StoreProduct> storeProductList = storeProductRepository.findAll(storeSpec(queryStoreProduct));
        for (StoreProduct storeProduct : storeProductList) {
            Long productId = storeProduct.getProductId();
            Product product = oAuth2RestTemplate.getForObject(productApiUrl + "/" + productId, Product.class);
            storeProduct.setProduct(product);
        }
        return storeProductList;
    }

    private Specification<StoreProduct> storeSpec(final StoreProduct storeProduct) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (storeProduct != null) {
                if (storeProduct.getStore() != null && storeProduct.getStore().getStoreId() != null) {
                    Join<StoreProduct, Store> join = root.join("store");
                    predicateList.add(cb.equal(join.<Long>get("storeId"), storeProduct.getStore().getStoreId()));
                }
                if (storeProduct.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), storeProduct.getEffective()));
                }
                if (storeProduct.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), storeProduct.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
