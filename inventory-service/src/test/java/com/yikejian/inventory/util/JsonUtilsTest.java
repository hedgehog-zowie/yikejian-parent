package com.yikejian.inventory.util;

import com.google.common.collect.Sets;
import com.yikejian.inventory.domain.inventory.Inventory;
import org.junit.*;
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

    }

    @Test
    public void inventoriesToJson(){
        Inventory inventory1 = new Inventory(1L, 1L, 4, 16, 16,  "20180120", "201801201000");
        Inventory inventory2 = new Inventory(1L, 1L, 4,16, 16,  "20180120", "201801201030");
        Inventory inventory3 = new Inventory(1L, 1L, 4, 16, 16, "20180120", "201801201100");
        Inventory inventory4 = new Inventory(1L, 1L, 4,16, 16, "20180120", "201801201130");
        Inventory inventory5 = new Inventory(1L, 2L, 4, 16, 16, "20180120", "201801201000");
        Inventory inventory6 = new Inventory(1L, 2L, 4, 16, 16, "20180120", "201801201030");
        Inventory inventory7 = new Inventory(1L, 2L, 4, 16, 16, "20180120", "201801201100");
        Inventory inventory8 = new Inventory(1L, 2L, 4, 16, 16, "20180120", "201801201130");
        Set inventorySet = Sets.newHashSet();
        inventorySet.add(inventory1);
        inventorySet.add(inventory2);
        inventorySet.add(inventory3);
        inventorySet.add(inventory4);
        inventorySet.add(inventory5);
        inventorySet.add(inventory6);
        inventorySet.add(inventory7);
        inventorySet.add(inventory8);
        System.out.println(JsonUtils.toJson(inventorySet));
    }

}
