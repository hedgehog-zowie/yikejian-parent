package com.yikejian.customer.service;

import com.yikejian.customer.domain.wechat.SuccessResponse;
import com.yikejian.customer.domain.wechat.ErrorResponse;
import com.yikejian.customer.util.JsonUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * <code>WechatLoginServiceTest</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/2/2 16:08
 */
public class WechatLoginServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WechatLoginServiceTest.class);
    private static RestTemplate restTemplate;

    private static final String URL_TEMPLATE = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
    private static final String APPID = "wxa85a2b5d75773381";
    private static final String SECRET = "c1c82e045cb1fb42acb87151e8a3574d";
    private static final String CODE = "013VUaOI1YKDZ40iIMPI1gS8OI1VUaOf";

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
    public void testLogin() {
        String url = String.format(URL_TEMPLATE, APPID, SECRET, CODE);
        ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            String responseString = (String) responseEntity.getBody();
            LOGGER.info(responseString);
            try {
                ErrorResponse errorResponse = JsonUtils.fromJson(responseString, ErrorResponse.class);
                LOGGER.info(JsonUtils.toJson(errorResponse));
            } catch (Exception e) {
                SuccessResponse successResponse = JsonUtils.fromJson(responseString, SuccessResponse.class);
                LOGGER.info(JsonUtils.toJson(successResponse));
            }
        }
    }

}
