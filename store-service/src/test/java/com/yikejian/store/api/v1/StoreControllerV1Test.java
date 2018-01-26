package com.yikejian.store.api.v1;

import com.google.common.collect.Sets;
import com.yikejian.store.api.v1.dto.RequestStore;
import com.yikejian.store.api.v1.dto.ResponseStore;
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
    private static final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTY5ODE3MDcsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUFJPRFVDVF9XUklURSIsIkRFVklDRV9SRUFEIiwiQk9PS19XUklURSIsIlVTRVJfV1JJVEUiLCJPUkRFUl9SRUFEIiwiQk9PS19SRUFEIiwiU1RPUkVfUkVBRCIsIkNVU1RPTUVSX1JFQUQiLCJPUkRFUl9XUklURSIsIlJPTEVfV1JJVEUiLCJDVVNUT01FUl9XUklURSIsIlNUT1JFX1dSSVRFIiwiREVWSUNFX1dSSVRFIiwiVVNFUl9SRUFEIiwiUk9MRV9SRUFEIiwiTE9HX1JFQUQiLCJQUk9EVUNUX1JFQUQiXSwianRpIjoiNDdiNDUzNTMtYThiMS00MjQwLWIyNTYtNzgzZjkyYjQ2ZWM0IiwiY2xpZW50X2lkIjoidHJ1c3RlZCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.Cs7Z99H6jYPqyOnx7iZYqL1DEW90Z2TtjU0c1RvojW2PPBdQXrkP8GOKV_nhY4qOidse-LF-ZGiCMNzdnW5d_7V1yB7ceGn4rRyuRzVcYQshb-gbEZG7SjBvtQvjGKR1QmAk82YizX2p3vt5rpkdzXPx0mP5uvJJGpnsr9LQXwk6oZioA7sUx7RjYLE7CxO_934gt_BiG7gsorXGxhBQXO4U3-pRnxiGLVHhdF052AzzO-HFx_BTy17KhO6cxBa6TXuvGA5Ju2HoHz5M1X704Pu8fOsGXpJLw3_bcE5FV6A3mtOez3Z0e3J-n8Yh96PcW6Rb8fRe2wuGb1gkFYM6wg";

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
        LOGGER.info(JsonUtils.toJson(store));
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
        LOGGER.info(JsonUtils.toJson(gotStore));
    }

    @Test
    public void testGet() {
        String getUrl = String.format(GET_STORE_URL_TEMPLATE, 1, ACCESS_TOKEN);
        Store gotStore = restTemplate.getForObject(getUrl, Store.class);
        assertEquals(1, gotStore.getStoreId().longValue());
        LOGGER.info(JsonUtils.toJson(gotStore));
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
        storeProduct1.setStartTime("1100");
        storeProduct1.setEndTime("1800");
        storeProduct1.setStore(store);
        StoreProduct storeProduct2 = new StoreProduct();
        storeProduct2.setProductId(2L);
        storeProduct2.setStartTime("1200");
        storeProduct2.setEndTime("1900");
        storeProduct2.setStore(store);
        Set<StoreProduct> storeProductSet = Sets.newHashSet();
        storeProductSet.add(storeProduct1);
        storeProductSet.add(storeProduct2);
        store.setStoreProductSet(storeProductSet);

        Device device1 = new Device();
        device1.setDeviceId(1L);
        device1.setStore(store);
        DeviceProduct deviceProduct11 = new DeviceProduct();
        deviceProduct11.setProductId(1L);
        deviceProduct11.setDevice(device1);
        DeviceProduct deviceProduct12 = new DeviceProduct();
        deviceProduct12.setProductId(2L);
        deviceProduct12.setDevice(device1);
        Set<DeviceProduct> deviceProductSet1 = Sets.newHashSet();
        deviceProductSet1.add(deviceProduct11);
        deviceProductSet1.add(deviceProduct12);
        device1.setDeviceProductSet(deviceProductSet1);

        Device device2 = new Device();
        device2.setDeviceId(2L);
        device2.setStore(store);
        DeviceProduct deviceProduct21 = new DeviceProduct();
        deviceProduct21.setProductId(1L);
        deviceProduct21.setDevice(device2);
        DeviceProduct deviceProduct22 = new DeviceProduct();
        deviceProduct22.setProductId(2L);
        deviceProduct22.setDevice(device2);
        Set<DeviceProduct> deviceProductSet2 = Sets.newHashSet();
        deviceProductSet2.add(deviceProduct21);
        deviceProductSet2.add(deviceProduct22);
        device2.setDeviceProductSet(deviceProductSet2);

        Device device3 = new Device();
        device3.setDeviceId(3L);
        device3.setStore(store);
        DeviceProduct deviceProduct31 = new DeviceProduct();
        deviceProduct31.setProductId(1L);
        deviceProduct31.setDevice(device3);
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
