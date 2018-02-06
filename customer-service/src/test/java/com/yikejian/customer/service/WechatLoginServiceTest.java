package com.yikejian.customer.service;

import com.google.common.collect.Maps;
import com.yikejian.customer.domain.user.User;
import com.yikejian.customer.domain.user.UserType;
import com.yikejian.customer.domain.wechat.ErrorResponse;
import com.yikejian.customer.domain.wechat.SuccessResponse;
import com.yikejian.customer.util.JsonUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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
    //    private static final String APPID = "wxa85a2b5d75773381";
//    private static final String SECRET = "c1c82e045cb1fb42acb87151e8a3574d";
    private static final String APPID = "wx74e6f71fdef09122";
    private static final String SECRET = "c1c82e045cb1fb42acb87151e8a3574d";
    private static final String CODE = "003zRMpY19wxnU0bLprY1vfZpY1zRMpL";

    private static final String TOKEN_URL = "http://localhost:8001/user-service/oauth/token";

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

    @Test
    public void testGetToken() {
        User user = new User("admin", UserType.STAFF, "admin");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic dHJ1c3RlZDpzZWNyZXQ=");
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
//        Map<String, String> params = Maps.newHashMap();
//        params.put("username", user.getName());
//        params.put("password", user.getName());
//        params.put("grant_type", "password");
        String params = String.format("username=%s&password=%s&grant_type=%s", user.getName(), user.getPassword(), "password");
        HttpEntity httpEntity = new HttpEntity(params, httpHeaders);
        OAuth2AccessToken oAuth2AccessToken = restTemplate.postForObject(TOKEN_URL, httpEntity, OAuth2AccessToken.class);
        System.out.println(JsonUtils.toJson(oAuth2AccessToken));
    }

}
