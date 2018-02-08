package com.yikejian.customer.util;

import com.google.common.collect.Sets;
import com.yikejian.customer.domain.customer.Customer;
import com.yikejian.customer.domain.customer.Gender;
import com.yikejian.customer.domain.customer.Opinion;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * <code>JsonUtilsTest</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/2/8 9:45
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
    public void testCustomerToJson() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("客户名");
        customer.setMobileNumber("13812345678");
        customer.setGender(Gender.男);
        Opinion opinion = new Opinion();
        opinion.setContent("xxxxxx");
        Set opinionSet = Sets.newHashSet();
        opinionSet.add(opinion);
        customer.setOpinionSet(opinionSet);
        LOGGER.info(JsonUtils.toJson(customer));
    }

}
