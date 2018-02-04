package com.yikejian.inventory.api.v1;

import com.google.common.collect.Sets;
import com.yikejian.inventory.domain.inventory.Inventory;
import com.yikejian.inventory.domain.store.Device;
import com.yikejian.inventory.domain.store.DeviceProduct;
import com.yikejian.inventory.domain.store.Store;
import com.yikejian.inventory.domain.store.StoreProduct;
import com.yikejian.inventory.service.InventoryServiceTest;
import com.yikejian.inventory.util.JsonUtils;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author jackalope
 * @Title: InventoryControllerTest
 * @Package com.yikejian.inventory.api.v1
 * @Description: TODO
 * @date 2018/1/30 23:52
 */
public class InventoryControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceTest.class);

    private static RestTemplate restTemplate;
    private static final String INIT_STORE_TEMPLATE = "http://localhost:8005/inventory-service/v1/inventory/store?access_token=%s";
    private static final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTczNzA5OTYsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUFJPRFVDVF9XUklURSIsIkRFVklDRV9SRUFEIiwiQk9PS19XUklURSIsIlVTRVJfV1JJVEUiLCJPUkRFUl9SRUFEIiwiQk9PS19SRUFEIiwiU1RPUkVfUkVBRCIsIkNVU1RPTUVSX1JFQUQiLCJPUkRFUl9XUklURSIsIlJPTEVfV1JJVEUiLCJDVVNUT01FUl9XUklURSIsIlNUT1JFX1dSSVRFIiwiREVWSUNFX1dSSVRFIiwiVVNFUl9SRUFEIiwiUk9MRV9SRUFEIiwiTE9HX1JFQUQiLCJQUk9EVUNUX1JFQUQiXSwianRpIjoiYjk4NTQ1ZWMtYjY3Mi00NmFhLWIyMjEtOGFmZWZiMjY1N2Y5IiwiY2xpZW50X2lkIjoidHJ1c3RlZCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.g199tP0MoRLEWeSPo7YgEEO4bKbI2KMX-T_xQC2K_D3W-sxWFLJ9nWZS6OrbFaF01EcSkWEgZGnBGP0fiMWLbPLlBEyBrrTe8tIACWHNoJjd1Uy5XB7nDC7hloJ62Olsu5N3jwvz4SjE6milf2-_taKN6qUBe7X2Fy-3O_V1Nmt9Mr5Vc3GuqZHNIklkXnUcss0kbVHc1Hbjl7CitYjTEflL_t8uj1Nuwyeonuhh9ffg2SjzmbzftA1koHlCpCtP8ejpcqMD-7f0-k42d4kdW4XOij3dv9WgELfSObGynYwyNILUd38nobPCoEwcg0u324Pw4snQmM8lNhyUvlEopQ";
    private static Store store;

    @BeforeClass
    public static void beforeClass() {
        restTemplate = new RestTemplate();

        store = new Store();
        store.setStoreId(1L);
        store.setStartTime("1000");
        store.setEndTime("1800");
        store.setUnitDuration(30);
        store.setUnitTimes(4);

        StoreProduct storeProduct1 = new StoreProduct();
        storeProduct1.setProductId(1L);
//        storeProduct1.setStore(store);
        StoreProduct storeProduct2 = new StoreProduct();
        storeProduct2.setProductId(2L);
//        storeProduct2.setStore(store);
        Set<StoreProduct> storeProductSet = Sets.newHashSet();
        storeProductSet.add(storeProduct1);
        storeProductSet.add(storeProduct2);
        store.setStoreProductSet(storeProductSet);

        Device device1 = new Device();
        device1.setDeviceId(1L);
//        device1.setStore(store);
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
//        device2.setStore(store);
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
//        device3.setStore(store);
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
    public void testInitStoreInventory() {
        String url = String.format(INIT_STORE_TEMPLATE, ACCESS_TOKEN);
//        LOGGER.info(JsonUtils.toJson(store));
        List<Inventory> inventoryList = restTemplate.postForObject(url, store, List.class);
        assertEquals(130, inventoryList.size());
    }

}
