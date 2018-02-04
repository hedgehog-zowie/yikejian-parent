package com.yikejian.store.api.v1;

import com.google.common.collect.Sets;
import com.yikejian.store.api.v1.dto.*;
import com.yikejian.store.domain.store.Device;
import com.yikejian.store.domain.store.DeviceProduct;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.domain.store.StoreProduct;
import com.yikejian.store.util.JsonUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

/**
 * <code>StoreControllerV1Test</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/26 10:52
 */
public class StoreControllerV1Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreControllerV1Test.class);

    private static RestTemplate restTemplate;
    private static final String POST_STORE_URL_TEMPLATE = "http://localhost:8003/store-service/v1/store?access_token=%s";
    private static final String PUT_STORE_URL_TEMPLATE = "http://localhost:8003/store-service/v1/store?access_token=%s";
    private static final String GET_STORE_URL_TEMPLATE = "http://localhost:8003/store-service/v1/store/%s?access_token=%s";
    private static final String GET_STORES_URL_TEMPLATE = "http://localhost:8003/store-service/v1/stores?access_token=%s";
    private static final String GET_STORES_URL_TEMPLATE2 = "http://localhost:8003/store-service/v1/stores?access_token=%s&params=%s";
    private static final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjAwMTc5MTUsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUFJPRFVDVF9XUklURSIsIkRFVklDRV9SRUFEIiwiQk9PS19XUklURSIsIlVTRVJfV1JJVEUiLCJPUkRFUl9SRUFEIiwiQk9PS19SRUFEIiwiU1RPUkVfUkVBRCIsIkNVU1RPTUVSX1JFQUQiLCJPUkRFUl9XUklURSIsIlJPTEVfV1JJVEUiLCJDVVNUT01FUl9XUklURSIsIlNUT1JFX1dSSVRFIiwiREVWSUNFX1dSSVRFIiwiVVNFUl9SRUFEIiwiUk9MRV9SRUFEIiwiTE9HX1JFQUQiLCJQUk9EVUNUX1JFQUQiXSwianRpIjoiZGMwYjE2NDMtZmE5NS00ZGEzLTkwNDQtNDIxOGMzZTZhZTM5IiwiY2xpZW50X2lkIjoidHJ1c3RlZCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.Tkjr4N6s8VoZWOsVTmQIqXrVV0WYnKWKhF4gcVWRXH5-KI3MtrgfqRW6vBOMoYFPao2FOhVR7GQ5nU3eQAdnfILTZJUJTWinxX1NlTL6ucEqOxm2-XgssXYFaORA4Y6Wei9XkV_q-Pq44snhghn9OWrjdLCgYhlI2RfleUU6AelU3iwBW1GzxsOdPC28dsrn-0oXo2qn0v0fVxRSmTDln5cZBQYjXhGtBV0PDJatgxfPIPR5cDYk30ilcAEgfbu3Fawpv3CU0U_qaoggY2pOJKgZcaUT18Qxie_OSEJXoFtbodGEtC-XksHScB5Xt1LxI_Up1Vi4nGt5slCheRLL8g";

    @BeforeClass
    public static void beforeClass() {
        restTemplate = new RestTemplate();
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
    public void testPost() {
        String postUrl = String.format(POST_STORE_URL_TEMPLATE, ACCESS_TOKEN);
        Store store = restTemplate.postForObject(postUrl, newStore("店铺xxx"), Store.class);
        assertEquals("店铺xxx", store.getStoreName());
        assertEquals(2, store.getStoreProductSet().size());
        assertEquals(3, store.getDeviceSet().size());
//        LOGGER.info(JsonUtils.toJson(store));
    }

    @Test
    public void testPut() {
        String putUrl = String.format(PUT_STORE_URL_TEMPLATE, ACCESS_TOKEN);
        Store store = newStore("店铺xxx_put");
        store.setStoreId(2L);
        restTemplate.put(putUrl, store);
        String getUrl = String.format(GET_STORE_URL_TEMPLATE, 2, ACCESS_TOKEN);
        Store gotStore = restTemplate.getForObject(getUrl, Store.class);
        assertEquals(2, gotStore.getStoreId().longValue());
        assertEquals("店铺xxx_put", gotStore.getStoreName());
//        LOGGER.info(JsonUtils.toJson(gotStore));
    }

    @Test
    public void testGet() {
        String getUrl = String.format(GET_STORE_URL_TEMPLATE, 3, ACCESS_TOKEN);
        Store gotStore = restTemplate.getForObject(getUrl, Store.class);
        assertEquals(3, gotStore.getStoreId().longValue());
        assertEquals(2, gotStore.getStoreProductSet().size());
        assertEquals(3, gotStore.getDeviceSet().size());
//        LOGGER.info(JsonUtils.toJson(gotStore));
    }

    @Test
    public void testGets() throws UnsupportedEncodingException {
        String getUrl = String.format(GET_STORES_URL_TEMPLATE, ACCESS_TOKEN);
        List<Store> storeList = restTemplate.getForObject(getUrl, List.class);
        assertEquals(2, storeList.size());
        LOGGER.info(JsonUtils.toJson(storeList));

        RequestStore requestStore = new RequestStore();
        String params = URLEncoder.encode(JsonUtils.toJson(requestStore), "UTF-8");
        String getUrl2 = String.format(GET_STORES_URL_TEMPLATE2, ACCESS_TOKEN, params);
        ResponseStore responseStore = restTemplate.getForObject(getUrl2, ResponseStore.class);
        assertEquals(1, responseStore.getPagination().getCurrent().intValue());
        assertEquals(100, responseStore.getPagination().getPageSize().intValue());
        assertEquals(2, responseStore.getList().size());
        LOGGER.info(JsonUtils.toJson(responseStore));
    }

    @Test
    public void testGetsFromClient() throws UnsupportedEncodingException {
        RequestStoreOfClient requestStoreOfClient = new RequestStoreOfClient();
        requestStoreOfClient.setLocation(new Location(1D, 2D));
        requestStoreOfClient.setPagination(new Pagination(1, 5));
        String jsonString = JsonUtils.toJson(requestStoreOfClient);
        LOGGER.info(jsonString);
        String params = URLEncoder.encode(jsonString, "UTF-8");
        LOGGER.info(params);
        String getUrl2 = String.format(GET_STORES_URL_TEMPLATE2, ACCESS_TOKEN, params);
        ResponseStoreOfClient responseStoreOfClient = restTemplate.getForObject(getUrl2, ResponseStoreOfClient.class);
        assertEquals(1, responseStoreOfClient.getPagination().getCurrent().intValue());
        assertEquals(5, responseStoreOfClient.getPagination().getPageSize().intValue());
        assertEquals(5, responseStoreOfClient.getList().size());
        LOGGER.info(JsonUtils.toJson(responseStoreOfClient));
    }

    private Store newStore(String name) {
        Store store = new Store();
        store.setStoreName(name);
        store.setAddress("店铺x地址");
        store.setPhoneNumber("店铺x电话");
        store.setStartTime("1030");
        store.setEndTime("2330");
        store.setUnitDuration(30);
        store.setUnitTimes(4);
        store.setTraffic("店铺x交通信息");
        store.setLongitude(12.32F);
        store.setLatitude(100.03F);
        store.setEffective(1);
        store.setDeleted(0);

        StoreProduct storeProduct1 = new StoreProduct();
        storeProduct1.setProductId(1L);
        storeProduct1.setStore(store);
        storeProduct1.setEffective(1);
        storeProduct1.setDeleted(0);
        StoreProduct storeProduct2 = new StoreProduct();
        storeProduct2.setProductId(2L);
        storeProduct2.setStore(store);
        storeProduct2.setEffective(1);
        storeProduct2.setDeleted(0);
        Set<StoreProduct> storeProductSet = Sets.newHashSet();
        storeProductSet.add(storeProduct1);
        storeProductSet.add(storeProduct2);
        store.setStoreProductSet(storeProductSet);

        Device device1 = new Device();
        device1.setStore(store);
        device1.setDeviceCode("device1");
        device1.setEffective(1);
        device1.setDeleted(0);
        DeviceProduct deviceProduct11 = new DeviceProduct();
        deviceProduct11.setProductId(1L);
        deviceProduct11.setDevice(device1);
        deviceProduct11.setEffective(1);
        deviceProduct11.setDeleted(0);
        DeviceProduct deviceProduct12 = new DeviceProduct();
        deviceProduct12.setProductId(2L);
        deviceProduct12.setDevice(device1);
        deviceProduct12.setEffective(1);
        deviceProduct12.setDeleted(0);
        Set<DeviceProduct> deviceProductSet1 = Sets.newHashSet();
        deviceProductSet1.add(deviceProduct11);
        deviceProductSet1.add(deviceProduct12);
        device1.setDeviceProductSet(deviceProductSet1);

        Device device2 = new Device();
        device2.setStore(store);
        device2.setEffective(1);
        device2.setDeleted(0);
        device2.setDeviceCode("device2");
        DeviceProduct deviceProduct21 = new DeviceProduct();
        deviceProduct21.setProductId(1L);
        deviceProduct21.setDevice(device2);
        deviceProduct21.setEffective(1);
        deviceProduct21.setDeleted(0);
        DeviceProduct deviceProduct22 = new DeviceProduct();
        deviceProduct22.setProductId(2L);
        deviceProduct22.setDevice(device2);
        deviceProduct22.setEffective(1);
        deviceProduct22.setDeleted(0);
        Set<DeviceProduct> deviceProductSet2 = Sets.newHashSet();
        deviceProductSet2.add(deviceProduct21);
        deviceProductSet2.add(deviceProduct22);
        device2.setDeviceProductSet(deviceProductSet2);

        Device device3 = new Device();
        device3.setStore(store);
        device3.setEffective(1);
        device3.setDeleted(0);
        device3.setDeviceCode("device3");
        DeviceProduct deviceProduct31 = new DeviceProduct();
        deviceProduct31.setProductId(1L);
        deviceProduct31.setDevice(device3);
        deviceProduct31.setEffective(1);
        deviceProduct31.setDeleted(0);
        Set<DeviceProduct> deviceProductSet3 = Sets.newHashSet();
        deviceProductSet3.add(deviceProduct31);
        device3.setDeviceProductSet(deviceProductSet3);

        Set<Device> deviceSet = Sets.newHashSet();
        deviceSet.add(device1);
        deviceSet.add(device2);
        deviceSet.add(device3);
        store.setDeviceSet(deviceSet);

        return store;
    }

}
