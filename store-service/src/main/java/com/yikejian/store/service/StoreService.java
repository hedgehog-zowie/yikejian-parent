package com.yikejian.store.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.store.api.v1.dto.Pagination;
import com.yikejian.store.api.v1.dto.RequestStore;
import com.yikejian.store.api.v1.dto.ResponseStore;
import com.yikejian.store.api.v1.dto.StoreDto;
import com.yikejian.store.domain.product.Product;
import com.yikejian.store.domain.store.Device;
import com.yikejian.store.domain.store.DeviceProduct;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.domain.store.StoreProduct;
import com.yikejian.store.repository.DeviceRepository;
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
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private DeviceRepository deviceRepository;
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Value("${yikejian.api.product.url}")
    private String productApiUrl;

    @Autowired
    public StoreService(StoreRepository storeRepository,
                        StoreProductRepository storeProductRepository,
                        DeviceRepository deviceRepository,
                        OAuth2RestTemplate oAuth2RestTemplate) {
        this.storeRepository = storeRepository;
        this.storeProductRepository = storeProductRepository;
        this.deviceRepository = deviceRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @HystrixCommand
    @Transactional
    public Store saveStore(StoreDto storeDto) {
        Store store = new Store();
        if (storeDto.getStoreId() != null) {
            store.setStoreId(storeDto.getStoreId());
            if (storeDto.getProducts() != null && storeDto.getProducts().size() > 0) {
                storeProductRepository.deleteByStore(store);
            }
            if (storeDto.getDevices() != null && storeDto.getDevices().size() > 0) {
                deviceRepository.deleteByStore(store);
            }
        }
        store.setEffective(1);
        store.setDeleted(0);
        Store newStore = transStore(store.fromStoreDto(storeDto));
        return storeRepository.save(newStore);
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

    @HystrixCommand
    public StoreDto getStoreById(Long storeId) {
        Store store = storeRepository.findByStoreId(storeId);
        StoreDto storeDto = checkStore(store);
        return storeDto;
    }

    @HystrixCommand
    public ResponseStore getAllStore() {
        return new ResponseStore((List<Store>) storeRepository.findAll());
    }

    @HystrixCommand
    public List<StoreDto> getAllEffectiveStores() {
        List<Store> storeList = storeRepository.findByEffectiveAndDeleted(1, 0);
        List<StoreDto> storeDtoList = Lists.newArrayList();
        for (Store store : storeList) {
            storeDtoList.add(checkStore(store));
        }
        return storeDtoList;
    }

    @HystrixCommand
    public ResponseStore getStores(RequestStore requestStore) {
        Pagination pagination;
        if (requestStore != null && requestStore.getPagination() != null) {
            pagination = requestStore.getPagination();
        } else {
            pagination = new Pagination();
        }

        String filed = "lastModifiedAt";
        Sort.Direction direction = Sort.Direction.DESC;
        if (requestStore != null && requestStore.getSorter() != null) {
            if (requestStore.getSorter().getField() != null) {
                filed = requestStore.getSorter().getField();
            }
            if ("ascend".equals(requestStore.getSorter().getOrder())) {
                direction = Sort.Direction.ASC;
            }
        }
        Sort sort = new Sort(direction, filed);

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent() - 1,
                pagination.getPageSize(),
                sort);
        Page<Store> page = storeRepository.findAll(storeSpec(requestStore.getStore()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());

//        for (Store store : page.getContent()) {
//            setProductName(store);
//        }

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

    private Store transStore(Store store) {
        Store newStore = store;
        if (store.getStoreId() != null) {
            Store oldStore = storeRepository.findByStoreId(store.getStoreId());
            newStore = oldStore.mergeOtherStore(store);
        }
        return newStore;
    }

    private StoreDto checkStore(Store store) {
        if (store == null) {
            return null;
        }
        Set<StoreProduct> storeProductSet = store.getStoreProductSet();
        Set<StoreProduct> effectiveStoreProductSet = Sets.newHashSet();
        for (StoreProduct storeProduct : storeProductSet) {
            if (storeProduct.getEffective() != 1 || storeProduct.getDeleted() != 0) {
                continue;
            }
            Long productId = storeProduct.getProductId();
            Product product = oAuth2RestTemplate.getForObject(productApiUrl + "/" + productId, Product.class);
            storeProduct.setProductName(product.getProductName());
            effectiveStoreProductSet.add(storeProduct);
        }
        store.setStoreProductSet(effectiveStoreProductSet);

        Set<Device> deviceSet = store.getDeviceSet();
        Set<Device> effectiveDeviceSet = Sets.newHashSet();
        for (Device device : deviceSet) {
            if (device.getEffective() != 1 || device.getDeleted() != 0) {
                continue;
            }
            effectiveDeviceSet.add(device);
            Set<DeviceProduct> deviceProductSet = device.getDeviceProductSet();
            Set<DeviceProduct> effectiveDeviceProductSet = Sets.newHashSet();
            for (DeviceProduct deviceProduct : deviceProductSet) {
                if (deviceProduct.getEffective() != 1 || deviceProduct.getDeleted() != 0) {
                    continue;
                }
                Long productId = deviceProduct.getProductId();
                Product product = oAuth2RestTemplate.getForObject(productApiUrl + "/" + productId, Product.class);
                deviceProduct.setProductName(product.getProductName());
                effectiveDeviceProductSet.add(deviceProduct);
            }
            device.setDeviceProductSet(effectiveDeviceProductSet);
        }
        store.setDeviceSet(effectiveDeviceSet);
        return store.toStoreDto();
    }

    @Deprecated
    private void setProductName(Store store) {
        if (store != null && store.getStoreProductSet() == null) return;
        for (StoreProduct storeProduct : store.getStoreProductSet()) {
            Long productId = storeProduct.getProductId();
            Product product = oAuth2RestTemplate.getForObject(productApiUrl + "/" + productId, Product.class);
            storeProduct.setProductName(product.getProductName());
        }
    }

}
