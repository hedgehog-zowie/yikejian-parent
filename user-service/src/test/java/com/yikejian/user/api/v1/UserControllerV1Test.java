package com.yikejian.user.api.v1;

import com.yikejian.user.api.v1.dto.UserDto;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
    private static final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTYyMTE5MzIsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUFJPRFVDVF9XUklURSIsIkRFVklDRV9SRUFEIiwiQk9PS19XUklURSIsIlVTRVJfV1JJVEUiLCJPUkRFUl9SRUFEIiwiQk9PS19SRUFEIiwiU1RPUkVfUkVBRCIsIkNVU1RPTUVSX1JFQUQiLCJPUkRFUl9XUklURSIsIlJPTEVfV1JJVEUiLCJDVVNUT01FUl9XUklURSIsIlNUT1JFX1dSSVRFIiwiREVWSUNFX1dSSVRFIiwiVVNFUl9SRUFEIiwiUk9MRV9SRUFEIiwiTE9HX1JFQUQiLCJQUk9EVUNUX1JFQUQiXSwianRpIjoiNGVjYjg5N2QtZDk4YS00ZTBhLWI0NGUtNDRhY2Q0NTgxNzUxIiwiY2xpZW50X2lkIjoidHJ1c3RlZCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.YGUuTfcHCKTkd_5lQfm-jFTkZlUccLw0QTTYz9zOrkDDgt0NAHsgHy7GTqu9EG1oWO-0tYwllw3TpT0DgQmB6iYWrKIFV_nqitDCEhxHXECa6CgLLTV8TZmkRRM-hW01z-YDWoPaDFjPfyWUYNkde1Jmpebdg-fmspn2Hy-F7W4X3XY7UQMOoVu5WUnDNWysaeIQkJJltyUCG3zJw20a8JhgntUjBZ9k5LpPA7innhjHxY3ZG9hRQZYQTvZb4aVxOQsrwwvxuwi6r4yKTBntKH4ucmhaTHVb9IAiGljZ6CIYglqIdrKHCnsdGVJwocQgi0KI2AXgS8caIJJyDXqqfQ";

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
        UserDto userDto = restTemplate.getForObject(String.format(ME_URL_TEMPLATE, ACCESS_TOKEN),
                UserDto.class);
        assertEquals("admin", userDto.getUserName());
    }

}
