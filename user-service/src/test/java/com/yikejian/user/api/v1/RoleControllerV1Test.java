package com.yikejian.user.api.v1;

import com.yikejian.user.api.v1.dto.RequestRole;
import com.yikejian.user.api.v1.dto.ResponseRole;
import com.yikejian.user.domain.role.Role;
import com.yikejian.user.util.JsonUtils;
import org.junit.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
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
    public void testGetRole() {
        String url = String.format(GET_ROLE_URL_TEMPLATE, 1, ACCESS_TOKEN);
        Role role = restTemplate.getForObject(url, Role.class);
        assertEquals("ADMIN", role.getRoleName());
        assertEquals("ROLE_READ,ROLE_WRITE,USER_READ,USER_WRITE,LOG_READ,CUSTOMER_READ,CUSTOMER_WRITE,ORDER_READ,ORDER_WRITE,STORE_READ,STORE_WRITE,PRODUCT_READ,PRODUCT_WRITE,DEVICE_READ,DEVICE_WRITE,BOOK_READ,BOOK_WRITE",
                role.getAuthorities());
    }

    @Test
    public void testGetRoles() throws UnsupportedEncodingException, URISyntaxException {
        Role role = new Role();
        role.setRoleName("ADMIN");
        RequestRole requestRole = new RequestRole();
        requestRole.setRole(role);
        String params = URLEncoder.encode(JsonUtils.toJson(requestRole), "UTF-8");
        String url = String.format(GET_ROLES_URL_TEMPLATE, ACCESS_TOKEN, params);
        ResponseRole responseRole = restTemplate.getForObject(url, ResponseRole.class);
//        String params = JsonUtils.toJson(requestRole);
//        String url = URLEncoder.encode(String.format(GET_ROLES_URL_TEMPLATE, ACCESS_TOKEN, params), "UTF-8");
//        URI uri = new URI(url);
//        ResponseRole responseRole = restTemplate.getForObject(uri, ResponseRole.class);

        assertEquals(1, responseRole.getRoleList().size());
        assertEquals(1, responseRole.getPagination().getTotalPages().intValue());
        assertEquals(1, responseRole.getPagination().getTotalSize().intValue());
        assertEquals(10, responseRole.getPagination().getPageSize().intValue());
        assertEquals(0, responseRole.getPagination().getCurrentPage().intValue());

//        String url2 = String.format(GET_ROLES_URL_TEMPLATE2, ACCESS_TOKEN);
//        ResponseRole responseRole2 = restTemplate.getForObject(url2, ResponseRole.class, requestRole);
//        System.out.println(responseRole2);
    }

    @Test
    public void testPostRole(){
        Role role = new Role();
        role.setRoleName("ADMIN2");
        role.setAuthorities("auth1,auth2");
        String url = String.format(POST_ROLE_URL_TEMPLATE, ACCESS_TOKEN);
        Role role2 = restTemplate.postForObject(url, role, Role.class);
        assertEquals("ADMIN2", role2.getRoleName());
        assertEquals("auth1,auth2", role2.getAuthorities());
    }

    @Test
    public void testPutRole()  {
        Role role = new Role();
        role.setRoleId(6L);
        role.setRoleName("ADMIN6");
        String url = String.format(PUT_ROLE_URL_TEMPLATE, ACCESS_TOKEN);
        restTemplate.put(url, role);
        String getUrl = String.format(GET_ROLE_URL_TEMPLATE, 6, ACCESS_TOKEN);
        Role role2 = restTemplate.getForObject(getUrl, Role.class);
        assertEquals(6, role2.getRoleId().intValue());
        assertEquals("ADMIN6", role2.getRoleName());
    }

}