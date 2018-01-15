package com.yikejian.user.service;

import com.yikejian.user.UserServiceApplication;
import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.RequestRoleDto;
import com.yikejian.user.api.v1.dto.ResponseRoleDto;
import com.yikejian.user.api.v1.dto.RoleDto;
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
        RoleDto role = roleService.getRoleById(1L);
        assertEquals(role.getRoleName(), "ADMIN");

        // add
        RoleDto role1 = new RoleDto("admin1", "auth1, auth2, auth3");
        RoleDto role2 = new RoleDto("admin2", "auth1, auth2, auth3, auth4, auth5, auth6");
        RoleDto role3 = new RoleDto("admin3", "auth4, auth5, auth6");
        roleService.saveRole(role1);
        roleService.saveRole(role2);
        roleService.saveRole(role3);
        assertEquals(8, roleService.getAll().getRoleList().size());

        // update
        role3.setRoleId(8L);
        role3.setAuthorities("auth1, auth2, auth3");
        roleService.saveRole(role3);
        assertEquals("auth1, auth2, auth3", roleService.getRoleById(8L).getAuthorities());
        assertEquals(8, roleService.getAll().getRoleList().size());

        // batch add
        RoleDto role4 = new RoleDto("admin4", "auth6, auth7, auth8");
        RoleDto role5 = new RoleDto("admin5", "auth1, auth2, auth3, auth4, auth5, auth6");
        RoleDto role6 = new RoleDto("admin6", "auth4, auth5, auth6");
        List<RoleDto> roleDtoList = Arrays.asList(role4, role5, role6);
        roleService.saveRoles(roleDtoList);
        assertEquals(11, roleService.getAll().getRoleList().size());

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
        assertEquals(11, roleService.getAll().getRoleList().size());

        // get roles
        RequestRoleDto requestRoleDto = new RequestRoleDto();
        ResponseRoleDto responseRoleDto = roleService.getRoles(requestRoleDto);
        assertEquals(2, responseRoleDto.getPagination().getTotalPages().intValue());
        assertEquals(11, responseRoleDto.getPagination().getTotalSize().intValue());
        assertEquals(0, responseRoleDto.getPagination().getCurrentPage().intValue());
        assertEquals(10, responseRoleDto.getPagination().getPageSize().intValue());
        assertEquals(10, responseRoleDto.getRoleList().size());

        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName("ADMIN");
        requestRoleDto.setRole(roleDto);
        responseRoleDto = roleService.getRoles(requestRoleDto);
        assertEquals(1, responseRoleDto.getPagination().getTotalPages().intValue());
        assertEquals(1, responseRoleDto.getPagination().getTotalSize().intValue());
        assertEquals(0, responseRoleDto.getPagination().getCurrentPage().intValue());
        assertEquals(10, responseRoleDto.getPagination().getPageSize().intValue());
        assertEquals(1, responseRoleDto.getRoleList().size());

        roleDto.setRoleName("admin");
        requestRoleDto.setRole(roleDto);
        responseRoleDto = roleService.getRoles(requestRoleDto);
        assertEquals(1, responseRoleDto.getPagination().getTotalPages().intValue());
        assertEquals(6, responseRoleDto.getPagination().getTotalSize().intValue());
        assertEquals(0, responseRoleDto.getPagination().getCurrentPage().intValue());
        assertEquals(10, responseRoleDto.getPagination().getPageSize().intValue());
        assertEquals(6, responseRoleDto.getRoleList().size());

        RoleDto role7 = new RoleDto("admin7", "auth6, auth7, auth8");
        RoleDto role8 = new RoleDto("admin8", "auth1, auth2, auth3, auth4, auth5, auth6");
        RoleDto role9 = new RoleDto("admin9", "auth4, auth5, auth6");
        RoleDto role10 = new RoleDto("admin10", "auth4, auth5, auth6");
        RoleDto role11 = new RoleDto("admin11", "auth4, auth5, auth6");
        roleService.saveRoles(Arrays.asList(role7, role8, role9, role10, role11));
        responseRoleDto = roleService.getRoles(requestRoleDto);
        assertEquals(2, responseRoleDto.getPagination().getTotalPages().intValue());
        assertEquals(11, responseRoleDto.getPagination().getTotalSize().intValue());
        assertEquals(0, responseRoleDto.getPagination().getCurrentPage().intValue());
        assertEquals(10, responseRoleDto.getPagination().getPageSize().intValue());
        assertEquals(10, responseRoleDto.getRoleList().size());

        requestRoleDto.setPagination(new Pagination(1, 10));
        responseRoleDto = roleService.getRoles(requestRoleDto);
        assertEquals(2, responseRoleDto.getPagination().getTotalPages().intValue());
        assertEquals(11, responseRoleDto.getPagination().getTotalSize().intValue());
        assertEquals(1, responseRoleDto.getPagination().getCurrentPage().intValue());
        assertEquals(10, responseRoleDto.getPagination().getPageSize().intValue());
        assertEquals(1, responseRoleDto.getRoleList().size());

        requestRoleDto.setPagination(new Pagination(2, 3));
        responseRoleDto = roleService.getRoles(requestRoleDto);
        assertEquals(4, responseRoleDto.getPagination().getTotalPages().intValue());
        assertEquals(11, responseRoleDto.getPagination().getTotalSize().intValue());
        assertEquals(2, responseRoleDto.getPagination().getCurrentPage().intValue());
        assertEquals(3, responseRoleDto.getPagination().getPageSize().intValue());
        assertEquals(3, responseRoleDto.getRoleList().size());
    }

}
