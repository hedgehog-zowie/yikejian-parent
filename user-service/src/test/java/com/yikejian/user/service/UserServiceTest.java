package com.yikejian.user.service;

import com.yikejian.user.UserServiceApplication;
import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.RequestUserDto;
import com.yikejian.user.api.v1.dto.ResponseUserDto;
import com.yikejian.user.api.v1.dto.UserDto;
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
 * <code>UserServiceTest</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 10:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

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
        UserDto user = userService.getUserById(1L);
        assertEquals(user.getUserName(), "admin");

        // add
        UserDto user1 = new UserDto("user1", 1L);
        UserDto user2 = new UserDto("user2", 2L);
        UserDto user3 = new UserDto("user3", 3L);
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        assertEquals(8, userService.getAll().getUserList().size());

        // update
        user3.setUserId(8L);
        user3.setRoleId(4L);
        userService.saveUser(user3);
        assertEquals(4, userService.getUserById(8L).getRoleId().intValue());
        assertEquals(8, userService.getAll().getUserList().size());

        // batch add
        UserDto user4 = new UserDto("user4", 1L);
        UserDto user5 = new UserDto("user5", 2L);
        UserDto user6 = new UserDto("user6", 3L);
        List<UserDto> userDtoList = Arrays.asList(user4, user5, user6);
        userService.saveUsers(userDtoList);
        assertEquals(11, userService.getAll().getUserList().size());

        // batch update
        user4.setUserId(9L);
        user4.setRoleId(1L);
        user5.setUserId(10L);
        user5.setRoleId(1L);
        user6.setUserId(11L);
        user6.setRoleId(1L);
        userService.saveUsers(Arrays.asList(user4, user5, user6));
        assertEquals(1, userService.getUserById(9L).getRoleId().intValue());
        assertEquals(1, userService.getUserById(10L).getRoleId().intValue());
        assertEquals(1, userService.getUserById(11L).getRoleId().intValue());
        assertEquals(11, userService.getAll().getUserList().size());

        // get users
        RequestUserDto requestUserDto = new RequestUserDto();
        ResponseUserDto responseUserDto = userService.getUsers(requestUserDto);
        assertEquals(2, responseUserDto.getPagination().getTotalPages().intValue());
        assertEquals(11, responseUserDto.getPagination().getTotalSize().intValue());
        assertEquals(0, responseUserDto.getPagination().getCurrentPage().intValue());
        assertEquals(10, responseUserDto.getPagination().getPageSize().intValue());
        assertEquals(10, responseUserDto.getUserList().size());

        UserDto userDto = new UserDto();
        userDto.setUserName("admin");
        requestUserDto.setUser(userDto);
        responseUserDto = userService.getUsers(requestUserDto);
        assertEquals(1, responseUserDto.getPagination().getTotalPages().intValue());
        assertEquals(1, responseUserDto.getPagination().getTotalSize().intValue());
        assertEquals(0, responseUserDto.getPagination().getCurrentPage().intValue());
        assertEquals(10, responseUserDto.getPagination().getPageSize().intValue());
        assertEquals(1, responseUserDto.getUserList().size());

        userDto.setUserName("user");
        requestUserDto.setUser(userDto);
        responseUserDto = userService.getUsers(requestUserDto);
        assertEquals(1, responseUserDto.getPagination().getTotalPages().intValue());
        assertEquals(6, responseUserDto.getPagination().getTotalSize().intValue());
        assertEquals(0, responseUserDto.getPagination().getCurrentPage().intValue());
        assertEquals(10, responseUserDto.getPagination().getPageSize().intValue());
        assertEquals(6, responseUserDto.getUserList().size());

        UserDto user7 = new UserDto("user7", 5L);
        UserDto user8 = new UserDto("user8", 5L);
        UserDto user9 = new UserDto("user9", 5L);
        UserDto user10 = new UserDto("user10", 5L);
        UserDto user11 = new UserDto("user11", 5L);
        userService.saveUsers(Arrays.asList(user7, user8, user9, user10, user11));
        responseUserDto = userService.getUsers(requestUserDto);
        assertEquals(2, responseUserDto.getPagination().getTotalPages().intValue());
        assertEquals(11, responseUserDto.getPagination().getTotalSize().intValue());
        assertEquals(0, responseUserDto.getPagination().getCurrentPage().intValue());
        assertEquals(10, responseUserDto.getPagination().getPageSize().intValue());
        assertEquals(10, responseUserDto.getUserList().size());

        requestUserDto.setPagination(new Pagination(1, 10));
        responseUserDto = userService.getUsers(requestUserDto);
        assertEquals(2, responseUserDto.getPagination().getTotalPages().intValue());
        assertEquals(11, responseUserDto.getPagination().getTotalSize().intValue());
        assertEquals(1, responseUserDto.getPagination().getCurrentPage().intValue());
        assertEquals(10, responseUserDto.getPagination().getPageSize().intValue());
        assertEquals(1, responseUserDto.getUserList().size());

        requestUserDto.setPagination(new Pagination(2, 3));
        responseUserDto = userService.getUsers(requestUserDto);
        assertEquals(4, responseUserDto.getPagination().getTotalPages().intValue());
        assertEquals(11, responseUserDto.getPagination().getTotalSize().intValue());
        assertEquals(2, responseUserDto.getPagination().getCurrentPage().intValue());
        assertEquals(3, responseUserDto.getPagination().getPageSize().intValue());
        assertEquals(3, responseUserDto.getUserList().size());
    }

}
