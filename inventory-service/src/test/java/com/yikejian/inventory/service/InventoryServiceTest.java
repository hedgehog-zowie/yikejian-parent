package com.yikejian.inventory.service;

import com.google.common.collect.Sets;
import com.yikejian.inventory.InventoryServiceApplication;
import com.yikejian.inventory.domain.inventory.Inventory;
import com.yikejian.inventory.domain.store.Device;
import com.yikejian.inventory.domain.store.DeviceProduct;
import com.yikejian.inventory.domain.store.Store;
import com.yikejian.inventory.domain.store.StoreProduct;
import com.yikejian.inventory.util.JsonUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * <code>InventoryServiceTest</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/25 10:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InventoryServiceApplication.class)
public class InventoryServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceTest.class);

    @Autowired
    private InventoryService inventoryService;

    private static Store store;

    @BeforeClass
    public static void beforeClass() {
        store = new Store();
        store.setStoreId(1L);
        store.setStartTime("1000");
        store.setEndTime("1800");
        store.setUnitDuration(30);
        store.setUnitTimes(4);

        StoreProduct storeProduct1 = new StoreProduct();
        storeProduct1.setProductId(1L);
        storeProduct1.setStore(store);
        StoreProduct storeProduct2 = new StoreProduct();
        storeProduct2.setProductId(2L);
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
    public void initInventoryOfStoreTest() {
        List<Inventory> inventoryList = inventoryService.initInventoryOfStore(store, "20180131");
        assertEquals(26, inventoryList.size());
        System.out.println("=================");
        System.out.println(JsonUtils.toJson(inventoryList));
        inventoryList = inventoryService.initInventoryOfStore(store, "20180201");
        assertEquals(26, inventoryList.size());
        System.out.println("=================");
        System.out.println(JsonUtils.toJson(inventoryList));
        inventoryList = inventoryService.initInventoryOfStore(store);
        assertEquals(130, inventoryList.size());
        System.out.println("=================");
        System.out.println(JsonUtils.toJson(inventoryList));
    }

}
