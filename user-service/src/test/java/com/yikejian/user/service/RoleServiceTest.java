package com.yikejian.user.service;

import com.yikejian.user.UserServiceApplication;
import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.RequestRole;
import com.yikejian.user.api.v1.dto.ResponseRole;
import com.yikejian.user.domain.role.Role;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * <code>RoleServiceTest</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 10:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

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
    public void test() {
        Role role = roleService.getRoleById(1L);
        assertEquals(role.getRoleName(), "ADMIN");

        // add
        Role role1 = new Role("admin1", "auth1, auth2, auth3");
        Role role2 = new Role("admin2", "auth1, auth2, auth3, auth4, auth5, auth6");
        Role role3 = new Role("admin3", "auth4, auth5, auth6");
        roleService.saveRole(role1);
        roleService.saveRole(role2);
        roleService.saveRole(role3);
        assertEquals(8, roleService.getAll().getList().size());

        // update
        role3.setRoleId(8L);
        role3.setAuthorities("auth1, auth2, auth3");
        roleService.saveRole(role3);
        assertEquals("auth1, auth2, auth3", roleService.getRoleById(8L).getAuthorities());
        assertEquals(8, roleService.getAll().getList().size());

        // batch add
        Role role4 = new Role("admin4", "auth6, auth7, auth8");
        Role role5 = new Role("admin5", "auth1, auth2, auth3, auth4, auth5, auth6");
        Role role6 = new Role("admin6", "auth4, auth5, auth6");
        List<Role> roleDtoList = Arrays.asList(role4, role5, role6);
        roleService.saveRoles(roleDtoList);
        assertEquals(11, roleService.getAll().getList().size());

        // batch update
        role4.setRoleId(9L);
        role4.setAuthorities("auth1, auth2, auth3");
        role5.setRoleId(10L);
        role5.setAuthorities("auth1, auth2, auth3");
        role6.setRoleId(11L);
        role6.setAuthorities("auth1, auth2, auth3");
        roleService.saveRoles(Arrays.asList(role4, role5, role6));
        assertEquals("auth1, auth2, auth3", roleService.getRoleById(9L).getAuthorities());
        assertEquals("auth1, auth2, auth3", roleService.getRoleById(10L).getAuthorities());
        assertEquals("auth1, auth2, auth3", roleService.getRoleById(11L).getAuthorities());
        assertEquals(11, roleService.getAll().getList().size());

        // get roles
        RequestRole requestRole = new RequestRole();
        ResponseRole responseRole = roleService.getRoles(requestRole);
        assertEquals(2, responseRole.getPagination().getTotalPages().intValue());
        assertEquals(11, responseRole.getPagination().getTotal().intValue());
        assertEquals(0, responseRole.getPagination().getCurrent().intValue());
        assertEquals(10, responseRole.getPagination().getPageSize().intValue());
        assertEquals(10, responseRole.getList().size());

        Role roleDto = new Role();
        roleDto.setRoleName("ADMIN");
        requestRole.setRole(roleDto);
        responseRole = roleService.getRoles(requestRole);
        assertEquals(1, responseRole.getPagination().getTotalPages().intValue());
        assertEquals(1, responseRole.getPagination().getTotal().intValue());
        assertEquals(0, responseRole.getPagination().getCurrent().intValue());
        assertEquals(10, responseRole.getPagination().getPageSize().intValue());
        assertEquals(1, responseRole.getList().size());

        roleDto.setRoleName("admin");
        requestRole.setRole(roleDto);
        responseRole = roleService.getRoles(requestRole);
        assertEquals(1, responseRole.getPagination().getTotalPages().intValue());
        assertEquals(6, responseRole.getPagination().getTotal().intValue());
        assertEquals(0, responseRole.getPagination().getCurrent().intValue());
        assertEquals(10, responseRole.getPagination().getPageSize().intValue());
        assertEquals(6, responseRole.getList().size());

        Role role7 = new Role("admin7", "auth6, auth7, auth8");
        Role role8 = new Role("admin8", "auth1, auth2, auth3, auth4, auth5, auth6");
        Role role9 = new Role("admin9", "auth4, auth5, auth6");
        Role role10 = new Role("admin10", "auth4, auth5, auth6");
        Role role11 = new Role("admin11", "auth4, auth5, auth6");
        roleService.saveRoles(Arrays.asList(role7, role8, role9, role10, role11));
        responseRole = roleService.getRoles(requestRole);
        assertEquals(2, responseRole.getPagination().getTotalPages().intValue());
        assertEquals(11, responseRole.getPagination().getTotal().intValue());
        assertEquals(0, responseRole.getPagination().getCurrent().intValue());
        assertEquals(10, responseRole.getPagination().getPageSize().intValue());
        assertEquals(10, responseRole.getList().size());

        requestRole.setPagination(new Pagination(1, 10));
        responseRole = roleService.getRoles(requestRole);
        assertEquals(2, responseRole.getPagination().getTotalPages().intValue());
        assertEquals(11, responseRole.getPagination().getTotal().intValue());
        assertEquals(1, responseRole.getPagination().getCurrent().intValue());
        assertEquals(10, responseRole.getPagination().getPageSize().intValue());
        assertEquals(1, responseRole.getList().size());

        requestRole.setPagination(new Pagination(2, 3));
        responseRole = roleService.getRoles(requestRole);
        assertEquals(4, responseRole.getPagination().getTotalPages().intValue());
        assertEquals(11, responseRole.getPagination().getTotal().intValue());
        assertEquals(2, responseRole.getPagination().getCurrent().intValue());
        assertEquals(3, responseRole.getPagination().getPageSize().intValue());
        assertEquals(3, responseRole.getList().size());
    }

}
