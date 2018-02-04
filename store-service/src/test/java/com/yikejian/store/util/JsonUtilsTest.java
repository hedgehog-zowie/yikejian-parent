package com.yikejian.store.util;

import com.google.common.collect.Sets;
import com.yikejian.store.api.v1.dto.RequestStore;
import com.yikejian.store.api.v1.dto.RequestStoreOfClient;
import com.yikejian.store.domain.product.Product;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.domain.store.StoreProduct;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * <code>StoreTest</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/23 11:36
 */
public class JsonUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilsTest.class);

    @BeforeClass
    public static void beforeClass() {
    }

    @AfterClass
    public static void afterClass() {
    }

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void storesToJson(){
        Store store1 = new Store();
        store1.setStoreId(1L);
        store1.setStoreName("店铺1");
        store1.setAddress("店铺1地址");
        store1.setPhoneNumber("店铺1电话");
        store1.setStartTime("1030");
        store1.setEndTime("2330");
        store1.setUnitDuration(30);
        store1.setUnitTimes(4);
        store1.setTraffic("店铺1交通信息");
        store1.setLongitude(12.32F);
        store1.setLatitude(100.03F);
        Store store2 = new Store();
        store2.setStoreId(2L);
        store2.setStoreName("店铺2");
        store2.setAddress("店铺2地址");
        store2.setPhoneNumber("店铺2电话");
        store2.setStartTime("1000");
        store2.setEndTime("2400");
        store2.setUnitDuration(30);
        store2.setUnitTimes(4);
        store2.setTraffic("店铺2交通信息");
        store2.setLongitude(23.32F);
        store2.setLatitude(160.03F);
        Set storeSet = Sets.newHashSet();
        storeSet.add(store1);
        storeSet.add(store2);
        System.out.println(JsonUtils.toJson(storeSet));
    }

    @Test
    public void storeProductsToJson(){
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

        StoreProduct storeProduct1 = new StoreProduct();
        storeProduct1.setProductId(1L);
        storeProduct1.setStore(store);
        StoreProduct storeProduct2 = new StoreProduct();
        storeProduct2.setProductId(2L);
        storeProduct2.setStore(store);

        Set<StoreProduct> storeProductSet = Sets.newHashSet();
        storeProductSet.add(storeProduct1);
        storeProductSet.add(storeProduct2);
        System.out.println(JsonUtils.toJson(storeProductSet));
    }

    @Test
    public void testClientRequest(){
        String string = "{a:b,c:d}";
        RequestStoreOfClient requestStoreOfClient = JsonUtils.fromJson(string, RequestStoreOfClient.class);
        RequestStore requestStore = JsonUtils.fromJson(string, RequestStore.class);
        System.out.println(JsonUtils.toJson(requestStoreOfClient));
        System.out.println(JsonUtils.toJson(requestStore));
    }

}
