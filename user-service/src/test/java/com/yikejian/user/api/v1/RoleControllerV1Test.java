package com.yikejian.user.api.v1;

import com.yikejian.user.UserServiceApplication;
import com.yikejian.user.api.v1.dto.RequestRoleDto;
import com.yikejian.user.api.v1.dto.ResponseRoleDto;
import com.yikejian.user.api.v1.dto.RoleDto;
import com.yikejian.user.api.v1.dto.UserDto;
import com.yikejian.user.util.JsonUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import static junit.framework.TestCase.assertEquals;

/**
 * <code>RoleControllerV1Test</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 15:23
 */
public class RoleControllerV1Test {

    private static RestTemplate restTemplate;
    private static final String POST_ROLE_URL_TEMPLATE = "http://localhost:8001/uaa/v1/role?access_token=%s";
    private static final String PUT_ROLE_URL_TEMPLATE = "http://localhost:8001/uaa/v1/role?access_token=%s";
    private static final String GET_ROLE_URL_TEMPLATE = "http://localhost:8001/uaa/v1/role/%s?access_token=%s";
    private static final String GET_ROLES_URL_TEMPLATE = "http://localhost:8001/uaa/v1/roles?access_token=%s&params=%s";
    private static final String GET_ROLES_URL_TEMPLATE2 = "http://localhost:8001/uaa/v1/roles2?access_token=%s";
    private static final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTYxMzQwOTcsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUFJPRFVDVF9XUklURSIsIkRFVklDRV9SRUFEIiwiQk9PS19XUklURSIsIlVTRVJfV1JJVEUiLCJPUkRFUl9SRUFEIiwiQk9PS19SRUFEIiwiU1RPUkVfUkVBRCIsIkNVU1RPTUVSX1JFQUQiLCJPUkRFUl9XUklURSIsIlJPTEVfV1JJVEUiLCJDVVNUT01FUl9XUklURSIsIlNUT1JFX1dSSVRFIiwiREVWSUNFX1dSSVRFIiwiVVNFUl9SRUFEIiwiUk9MRV9SRUFEIiwiTE9HX1JFQUQiLCJQUk9EVUNUX1JFQUQiXSwianRpIjoiNmQzZjA1NTktMDk3ZS00YjAyLWIwMzctNjcwZTBmNDM1NjMwIiwiY2xpZW50X2lkIjoidHJ1c3RlZCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.PtFQwBb7Ka121teHIKb1sQpT3lKsep3N7umBN7X-AMmpEaYdQ8DQEsvwJJF7zOPjG-uJ1yY9BsyFeBjslMHTYnGmEmA0rDXzqnAobKCeK-yCWs2eJ5YOkaS3PGX-f8Cx-krM6MeaqOT5C3-OMeJFUva1XuhVCrQ2YFrP9OSXKbE";

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
    public void testGetRole() {
        String url = String.format(GET_ROLE_URL_TEMPLATE, 1, ACCESS_TOKEN);
        RoleDto roleDto = restTemplate.getForObject(url, RoleDto.class);
        assertEquals("ADMIN", roleDto.getRoleName());
        assertEquals("ROLE_READ,ROLE_WRITE,USER_READ,USER_WRITE,LOG_READ,CUSTOMER_READ,CUSTOMER_WRITE,ORDER_READ,ORDER_WRITE,STORE_READ,STORE_WRITE,PRODUCT_READ,PRODUCT_WRITE,DEVICE_READ,DEVICE_WRITE,BOOK_READ,BOOK_WRITE",
                roleDto.getAuthorities());
    }

    @Test
    public void testGetRoles() throws UnsupportedEncodingException, URISyntaxException {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName("ADMIN");
        RequestRoleDto requestRoleDto = new RequestRoleDto();
        requestRoleDto.setRole(roleDto);
        String params = URLEncoder.encode(JsonUtils.toJson(requestRoleDto), "UTF-8");
        String url = String.format(GET_ROLES_URL_TEMPLATE, ACCESS_TOKEN, params);
        ResponseRoleDto responseRoleDto = restTemplate.getForObject(url, ResponseRoleDto.class);
//        String params = JsonUtils.toJson(requestRoleDto);
//        String url = URLEncoder.encode(String.format(GET_ROLES_URL_TEMPLATE, ACCESS_TOKEN, params), "UTF-8");
//        URI uri = new URI(url);
//        ResponseRoleDto responseRoleDto = restTemplate.getForObject(uri, ResponseRoleDto.class);

        assertEquals(1, responseRoleDto.getRoleList().size());
        assertEquals(1, responseRoleDto.getPagination().getTotalPages().intValue());
        assertEquals(1, responseRoleDto.getPagination().getTotalSize().intValue());
        assertEquals(10, responseRoleDto.getPagination().getPageSize().intValue());
        assertEquals(0, responseRoleDto.getPagination().getCurrentPage().intValue());

        String url2 = String.format(GET_ROLES_URL_TEMPLATE2, ACCESS_TOKEN);
        ResponseRoleDto responseRoleDto2 =  restTemplate.getForObject(url2, ResponseRoleDto.class, requestRoleDto);
        System.out.println(responseRoleDto2);
    }

    @Test
    public void testPostRole(){
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName("ADMIN2");
        roleDto.setAuthorities("auth1,auth2");
        String url = String.format(POST_ROLE_URL_TEMPLATE, ACCESS_TOKEN);
        RoleDto roleDto2 = restTemplate.postForObject(url, roleDto, RoleDto.class);
        assertEquals("ADMIN2", roleDto2.getRoleName());
        assertEquals("auth1,auth2", roleDto2.getAuthorities());
    }

    @Test
    public void testPutRole()  {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(6L);
        roleDto.setRoleName("ADMIN6");
        String url = String.format(PUT_ROLE_URL_TEMPLATE, ACCESS_TOKEN);
        restTemplate.put(url, roleDto);
        String getUrl = String.format(GET_ROLE_URL_TEMPLATE, 6, ACCESS_TOKEN);
        RoleDto roleDto2 = restTemplate.getForObject(getUrl, RoleDto.class);
        assertEquals(6, roleDto2.getRoleId().intValue());
        assertEquals("ADMIN6", roleDto2.getRoleName());
    }

}
