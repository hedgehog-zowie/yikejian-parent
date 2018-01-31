package com.yikejian.store.config;

import com.google.common.collect.Sets;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.domain.store.StoreProduct;
import com.yikejian.store.repository.StoreRepository;
import com.yikejian.store.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <code>DatabaseInitializer</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/26 10:56
 */
@Service
@Profile({"development"})
public class DatabaseInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);

    private StoreRepository storeRepository;

    @Autowired
    public DatabaseInitializer(StoreRepository storeRepository){
        this.storeRepository = storeRepository;
    }

    public void initForDevelop(){
        Store store = new Store();
        store.setStoreId(1L);
        store.setStoreName("店铺1");
        store.setAddress("店铺1地址");
        store.setPhoneNumber("店铺1电话");
        store.setStartTime("1030");
        store.setEndTime("2330");
        store.setUnitDuration(30);
        store.setUnitTimes(4);
        store.setTraffic("店铺1交通信息");
        store.setLongitude(12.32F);
        store.setLatitude(100.03F);
        store.setEffective(1);
        store.setDeleted(0);

        StoreProduct storeProduct1 = new StoreProduct();
        storeProduct1.setProductId(1L);
        storeProduct1.setEffective(1);
        storeProduct1.setDeleted(0);
        storeProduct1.setStore(store);
        StoreProduct storeProduct2 = new StoreProduct();
        storeProduct2.setProductId(2L);
        storeProduct2.setEffective(1);
        storeProduct2.setDeleted(0);
        storeProduct2.setStore(store);

        Set<StoreProduct> storeProductSet = Sets.newHashSet();
        storeProductSet.add(storeProduct1);
        storeProductSet.add(storeProduct2);
        store.setStoreProductSet(storeProductSet);

        storeRepository.save(store);
    }

}
