package com.yikejian.store.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.store.api.v1.dto.Location;
import com.yikejian.store.api.v1.dto.Pagination;
import com.yikejian.store.api.v1.dto.RequestStore;
import com.yikejian.store.api.v1.dto.RequestStoreOfClient;
import com.yikejian.store.api.v1.dto.ResponseStore;
import com.yikejian.store.api.v1.dto.ResponseStoreOfClient;
import com.yikejian.store.api.v1.dto.StoreDto;
import com.yikejian.store.domain.inventory.Inventory;
import com.yikejian.store.domain.product.Product;
import com.yikejian.store.domain.store.Device;
import com.yikejian.store.domain.store.DeviceProduct;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.domain.store.StoreProduct;
import com.yikejian.store.domain.user.User;
import com.yikejian.store.exception.StoreServiceException;
import com.yikejian.store.repository.DeviceRepository;
import com.yikejian.store.repository.ImageRepository;
import com.yikejian.store.repository.StoreProductRepository;
import com.yikejian.store.repository.StoreRepository;
import com.yikejian.store.util.DateUtils;
import com.yikejian.store.util.DistanceUtils;
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
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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

    private static final Integer BOOKABLE_DAYS = 5;

    private StoreRepository storeRepository;
    private StoreProductRepository storeProductRepository;
    private DeviceRepository deviceRepository;
    private ImageRepository imageRepository;
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Value("${yikejian.api.user.url}")
    private String userUrl;
    @Value("${yikejian.api.product.url}")
    private String productApiUrl;
    @Value("${yikejian.api.inventory.url}")
    private String inventoryApiUrl;

    @Autowired
    public StoreService(StoreRepository storeRepository,
                        StoreProductRepository storeProductRepository,
                        DeviceRepository deviceRepository,
                        ImageRepository imageRepository,
                        OAuth2RestTemplate oAuth2RestTemplate) {
        this.storeRepository = storeRepository;
        this.storeProductRepository = storeProductRepository;
        this.deviceRepository = deviceRepository;
        this.imageRepository = imageRepository;
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
            if (storeDto.getImages() != null && storeDto.getImages().size() > 0) {
                imageRepository.deleteByStore(store);
            }
        }
        store.setEffective(1);
        store.setDeleted(0);
        Store newStore = transStore(store.fromStoreDto(storeDto));
        Store savedStore = storeRepository.save(newStore);
        try {
            List<Inventory> inventoryList = oAuth2RestTemplate.postForObject(inventoryApiUrl, savedStore, List.class);
            if (inventoryList.size() == 0) {
                throw new StoreServiceException("init inventory error.");
            }
        } catch (Exception e) {
            throw new StoreServiceException(e);
        }
        return savedStore;
    }

    @HystrixCommand
    public Store saveStore(Store store) {
        return storeRepository.save(transStore(store));
    }

    @HystrixCommand
    public List<Store> saveStores(List<Store> storeList) {
        List<Store> newStoreList = Lists.newArrayList();
        for (Store store : storeList) {
            Store newStore = transStore(store);
            newStoreList.add(newStore);
            List<Inventory> inventoryList = oAuth2RestTemplate.postForObject(inventoryApiUrl, newStore, List.class);
            if (inventoryList.size() == 0) {
                throw new StoreServiceException("init inventory error.");
            }
        }
        return (List<Store>) storeRepository.save(newStoreList);
    }

    @HystrixCommand
    public List<String> getBookableDaysByStoreId(Long storeId) {
        Store store = storeRepository.findByStoreId(storeId);
        int workDayStart = store.getWorkDayStart();
        int workDayEnd = store.getWorkDayEnd();
        LocalDate startDate = LocalDate.now();
        List<String> bookableDays = Lists.newArrayList();
        int i = 0;
        while (i < BOOKABLE_DAYS) {
            LocalDate currentDate = startDate.plusDays(i);
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            if (dayOfWeek.getValue() <= workDayEnd && dayOfWeek.getValue() >= workDayStart) {
                bookableDays.add(DateUtils.dateToDayStr(currentDate));
            }
            i++;
        }
        return bookableDays;
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
    public List<StoreDto> getAllEffectiveStores(Principal principal) {
        List<Store> storeList = Lists.newArrayList();
        if (principal == null) {
            storeList = storeRepository.findByEffectiveAndDeleted(1, 0);
        } else {
            User user = oAuth2RestTemplate.getForObject(userUrl, User.class);
            if (user.getStoreId() == null) {
                storeList = storeRepository.findByEffectiveAndDeleted(1, 0);
            } else {
                storeList.add(storeRepository.findByStoreId(user.getStoreId()));
            }
        }
        List<StoreDto> storeDtoList = Lists.newArrayList();
        for (Store store : storeList) {
            storeDtoList.add(checkStore(store));
        }
        return storeDtoList;
    }

    @HystrixCommand
    public ResponseStoreOfClient getStores(RequestStoreOfClient requestStoreOfClient) {
        Pagination pagination = requestStoreOfClient.getPagination();
        Location location = requestStoreOfClient.getLocation();
        List<StoreDto> storeDtoList = getAllEffectiveStores(null);
        for (StoreDto storeDto : storeDtoList) {
            storeDto.setDistance(DistanceUtils.Distance(location.getLatitude(), location.getLongitude(), storeDto.getLatitude(), storeDto.getLongitude()));
        }
        Collections.sort(storeDtoList);
        List<StoreDto> resultList = Lists.newArrayList();
        int i = 0;
        int start = (pagination.getCurrent() - 1) * pagination.getPageSize();
        boolean inPage = false;
        for (StoreDto storeDto : storeDtoList) {
            if (i == start) {
                inPage = true;
            }
            if (inPage) {
                resultList.add(storeDto);
            }
            if (resultList.size() == pagination.getPageSize()) {
                break;
            }
            i++;
        }
        pagination.setTotal((long) storeDtoList.size());
        pagination.setTotalPages((int) Math.ceil(storeDtoList.size() / pagination.getPageSize()));
        return new ResponseStoreOfClient(resultList, pagination);
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
//            setProductInfo(store);
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
            storeProduct.setProduct(product);
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
    private void setProductInfo(Store store) {
        if (store != null && store.getStoreProductSet() == null) return;
        for (StoreProduct storeProduct : store.getStoreProductSet()) {
            Long productId = storeProduct.getProductId();
            Product product = oAuth2RestTemplate.getForObject(productApiUrl + "/" + productId, Product.class);
            storeProduct.setProduct(product);
        }
    }

}
