package com.yikejian.payment.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.yikejian.payment.config.MyWechatPayConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <code>WechatPaymentServiceTest</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/2/9 16:30
 */
public class WechatPayServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatPayServiceTest.class);

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

    // 统一下单
    @Test
    public void testPrepay() throws Exception {
        MyWechatPayConfig config = new MyWechatPayConfig();
        WXPay wxpay = new WXPay(config);
//        WXPay wxpay = new WXPay(config, WXPayConstants.SignType.MD5, true);

        Map<String, String> data = new HashMap<>();
        data.put("body", "一刻间--客户预约");
        data.put("out_trade_no", "201801302136470001");
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://payment-service/wxpay/notify");
        data.put("trade_type", "JSAPI");
        data.put("openid", "o4Jn40P9ISpDXMANjUwftD4xdelQ");
        data.put("time_start", "20180211114010");
        data.put("time_expire", "20180211115510");

        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
