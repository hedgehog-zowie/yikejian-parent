package com.yikejian.user.api.v1;

import com.yikejian.user.domain.user.User;
import org.junit.*;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.assertEquals;

/**
 * <code>UserControllerV1Test</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 15:23
 */
public class UserControllerV1Test {

    private static RestTemplate restTemplate;
    private static final String ME_URL_TEMPLATE = "http://localhost:8001/uaa/v1/me?access_token=%s";
    private static final String GET_USER_URL_TEMPLATE = "http://localhost:8001/uaa/v1/user/%s?access_token=%s";
    private static final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTY0NjE5MjksInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUFJPRFVDVF9XUklURSIsIkRFVklDRV9SRUFEIiwiQk9PS19XUklURSIsIlVTRVJfV1JJVEUiLCJPUkRFUl9SRUFEIiwiQk9PS19SRUFEIiwiU1RPUkVfUkVBRCIsIkNVU1RPTUVSX1JFQUQiLCJPUkRFUl9XUklURSIsIlJPTEVfV1JJVEUiLCJDVVNUT01FUl9XUklURSIsIlNUT1JFX1dSSVRFIiwiREVWSUNFX1dSSVRFIiwiVVNFUl9SRUFEIiwiUk9MRV9SRUFEIiwiTE9HX1JFQUQiLCJQUk9EVUNUX1JFQUQiXSwianRpIjoiYzQ0YzU1MGUtNzhjZC00MThkLTk3ZDktZjNkODE2YmMwMzc0IiwiY2xpZW50X2lkIjoidHJ1c3RlZCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.hbcmxEvXx2wgw6dErqMjG8QhaLPSqJPXi4NjLOahhux7jRTgsl5j_bqYqRKt5mHWQh_zWvL8zhtVi71yAUqiHMaJLEM6bpmjPyN3qsh5LlaFh_6J_NqcTxZ-9NyEKLPdcySBboyMwdvFohtBAQFWVp-cY0FkWAgu_MfIhqxTOrUFTDihSn7Pdzm1IBBwsLvMMwusLBi35l1fVsb5yX92YhwzWWuTWX7Vb43i8v0vK1peRHC28WOxNHQDffJ6nmTaknk6J4OLYtBxbRAFAo2Q422dtWEXT4h6s8Da807ZpBVYfskkI85y-4XLL8Vh8rDWuSh_lBc3RIVYe62JQ5WWDg";

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
    public void testMe() {
        User user = restTemplate.getForObject(String.format(ME_URL_TEMPLATE, ACCESS_TOKEN),
                User.class);
        assertEquals("admin", user.getName());
    }

    @Test
    public void testGetUser() {
        String url = String.format(GET_USER_URL_TEMPLATE, 1, ACCESS_TOKEN);
        User user = restTemplate.getForObject(url, User.class);
        assertEquals("admin", user.getName());
        assertEquals("ADMIN", user.getRole().getRoleName());
        assertEquals("ROLE_READ,ROLE_WRITE,USER_READ,USER_WRITE,LOG_READ,CUSTOMER_READ,CUSTOMER_WRITE,ORDER_READ,ORDER_WRITE,STORE_READ,STORE_WRITE,PRODUCT_READ,PRODUCT_WRITE,DEVICE_READ,DEVICE_WRITE,INVENTORY_READ,INVENTORY_WRITE",
                user.getRole().getAuthorities());
    }

}
