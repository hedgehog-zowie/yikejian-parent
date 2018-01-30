package com.yikejian.order.util;

import com.google.common.collect.Sets;
import com.yikejian.order.domain.order.Order;
import com.yikejian.order.domain.order.OrderExtra;
import com.yikejian.order.domain.order.OrderItem;
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
    public void orderToJson(){
        Order order = new Order();
        order.setMobileNumber("1234");
        order.setCustomerName("客户1");
        order.setStoreId(1L);
        order.setStoreName("店铺1");
        order.setAmount(100.0D);
        order.setActualAmount(90.0D);
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProductId(1L);
        orderItem1.setProductName("产品1");
        orderItem1.setExperiencer("项目1使用者");
        orderItem1.setBookedTime("201801201300");

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProductId(1L);
        orderItem2.setProductName("产品1");
        orderItem2.setExperiencer("项目2使用者");
        orderItem2.setBookedTime("201801201400");
        OrderItem orderItem3 = new OrderItem();
        orderItem3.setProductId(2L);
        orderItem3.setProductName("产品2");
        orderItem3.setExperiencer("项目3使用者");
        orderItem3.setBookedTime("201801201500");
        Set<OrderItem> orderItemSet = Sets.newHashSet();
        orderItemSet.add(orderItem1);
        orderItemSet.add(orderItem2);
        orderItemSet.add(orderItem3);
        order.setOrderItems(orderItemSet);

        OrderExtra extra = new OrderExtra();
        extra.setDrink("饮料");
        extra.setRemark("备注");
        extra.setOrder(order);

        System.out.println(JsonUtils.toJson(order));
        System.out.println(JsonUtils.toJson(extra));
    }

}
