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
    private static final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTY1NTk0MDYsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwiUFJPRFVDVF9XUklURSIsIkRFVklDRV9SRUFEIiwiQk9PS19XUklURSIsIlVTRVJfV1JJVEUiLCJPUkRFUl9SRUFEIiwiQk9PS19SRUFEIiwiU1RPUkVfUkVBRCIsIkNVU1RPTUVSX1JFQUQiLCJPUkRFUl9XUklURSIsIlJPTEVfV1JJVEUiLCJDVVNUT01FUl9XUklURSIsIlNUT1JFX1dSSVRFIiwiREVWSUNFX1dSSVRFIiwiVVNFUl9SRUFEIiwiUk9MRV9SRUFEIiwiTE9HX1JFQUQiLCJQUk9EVUNUX1JFQUQiXSwianRpIjoiNjI1MjEzOGMtOGUwMS00NDMyLWE2ZmUtM2EyNzIzYzc2Njc0IiwiY2xpZW50X2lkIjoidHJ1c3RlZCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.GR9xtFI0mfHW7kAOcLd3PjgQIKlXCRZ0BxM5YlkDPrrS4kk7_x5LTMuhPua3OP6T9CW5beYVXgVtn5Fvgw2qzILkoemWmdl0xOz02s9RVHldd5zZBhpD-E24ZoVQk181fss1Ey6QCaqWtJ83KqxOg5gzOQHYBJVL5u3uv5KLaC_oWbykFyf3P430OGy_VSnKCA1m0d2O7bAgR1faC03iX3rPsae1mxh9hb4wY6M2PujtuXblt5-ZOVKIE1UJ5NFSdNivWIhXpPmZYwjo1EhWKUiTdZkVA3Cw0WgZNmskhiW-WTav7ARVtFV7drwFhQOMn3Pyplifna7JQOx-iTtvPg";

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
    public void testJson(){
        String roleString = "{\"roleId\":1,\"roleName\":\"ADMIN\",\"authorities\":\"ROLE_READ,ROLE_WRITE,USER_READ,USER_WRITE,LOG_READ,CUSTOMER_READ,CUSTOMER_WRITE,ORDER_READ,ORDER_WRITE,STORE_READ,STORE_WRITE,PRODUCT_READ,PRODUCT_WRITE,DEVICE_READ,DEVICE_WRITE,INVENTORY_READ,INVENTORY_WRITE\",\"createdBy\":\"sys\",\"createdAt\":\"Jan 20, 2018 1:14:13 PM\",\"lastModifiedBy\":\"sys\",\"lastModifiedAt\":\"Jan 20, 2018 1:14:13 PM\",\"effective\":1,\"deleted\":0}";
        Role role = JsonUtils.fromJson(roleString, Role.class);
        String roleString2 = "{\"roleId\":1,\"roleName\":\"ADMIN\",\"authorities\":\"ROLE_READ\":\"20180101\",\"effective\":1,\"deleted\":0}";
        Role role2 = JsonUtils.fromJson(roleString2, Role.class);
        System.out.println(role2);
    }

    @Test
    public void testGetRole() {
        String url = String.format(GET_ROLE_URL_TEMPLATE, 1, ACCESS_TOKEN);
        Role role = restTemplate.getForObject(url, Role.class);
        assertEquals("ADMIN", role.getRoleName());
        assertEquals("ROLE_READ,ROLE_WRITE,USER_READ,USER_WRITE,LOG_READ,CUSTOMER_READ,CUSTOMER_WRITE,ORDER_READ,ORDER_WRITE,STORE_READ,STORE_WRITE,PRODUCT_READ,PRODUCT_WRITE,DEVICE_READ,DEVICE_WRITE,INVENTORY_READ,INVENTORY_WRITE",
                role.getAuthorities());
        assertEquals(null, role.getUserSet());
        System.out.println(JsonUtils.toJson(role));
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

        assertEquals(1, responseRole.getList().size());
        assertEquals(1, responseRole.getPagination().getTotalPages().intValue());
        assertEquals(1, responseRole.getPagination().getTotal().intValue());
        assertEquals(10, responseRole.getPagination().getPageSize().intValue());
        assertEquals(0, responseRole.getPagination().getCurrent().intValue());

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
        Role role = new Role(8L,"ADMIN6", "auth1,auth2");
        String url = String.format(PUT_ROLE_URL_TEMPLATE, ACCESS_TOKEN);
        restTemplate.put(url, role);
        String getUrl = String.format(GET_ROLE_URL_TEMPLATE, 6, ACCESS_TOKEN);
        Role role2 = restTemplate.getForObject(getUrl, Role.class);
        assertEquals(8, role2.getRoleId().intValue());
        assertEquals("ADMIN6", role2.getRoleName());
    }

}