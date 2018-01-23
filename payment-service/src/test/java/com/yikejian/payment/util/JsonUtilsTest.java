package com.yikejian.payment.util;

import com.yikejian.payment.domain.payment.Payment;
import com.yikejian.payment.domain.payment.PaymentType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void paymentToJson(){
        Payment payment = new Payment();
        payment.setCustomerId(1L);
        payment.setOrderId(1L);
        payment.setAmount(99.0D);
        payment.setPaymentType(PaymentType.ACCOUNT);
        System.out.println(JsonUtils.toJson(payment));
    }

}
