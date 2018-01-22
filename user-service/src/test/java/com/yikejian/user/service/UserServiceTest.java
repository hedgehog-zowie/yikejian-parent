package com.yikejian.user.service;

import com.yikejian.user.UserServiceApplication;
import com.yikejian.user.api.v1.dto.Pagination;
import com.yikejian.user.api.v1.dto.RequestUser;
import com.yikejian.user.api.v1.dto.ResponseUser;
import com.yikejian.user.domain.role.Role;
import com.yikejian.user.domain.user.User;
import org.junit.*;
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
        User user = userService.getUserById(1L);
        assertEquals(user.getName(), "admin");

        // add
        User user1 = new User("user1", "", new Role(1L));
        User user2 = new User("user2", "", new Role(2L));
        User user3 = new User("user3", "", new Role(3L));
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        assertEquals(8, userService.getAll().getList().size());

        // update
        user3.setId(8L);
        user3.setRole(new Role(4L));
        userService.saveUser(user3);
        assertEquals(4, userService.getUserById(8L).getRole().getRoleId().intValue());
        assertEquals(8, userService.getAll().getList().size());

        // batch add
        User user4 = new User("user4", "", new Role(1L));
        User user5 = new User("user5", "", new Role(2L));
        User user6 = new User("user6", "", new Role(3L));
        List<User> userDtoList = Arrays.asList(user4, user5, user6);
        userService.saveUsers(userDtoList);
        assertEquals(11, userService.getAll().getList().size());

        // batch update
        user4.setId(9L);
        user4.setRole(new Role(1L));
        user5.setId(10L);
        user5.setRole(new Role(1L));
        user6.setId(11L);
        user6.setRole(new Role(1L));
        userService.saveUsers(Arrays.asList(user4, user5, user6));
        assertEquals(1, userService.getUserById(9L).getRole().getRoleId().intValue());
        assertEquals(1, userService.getUserById(10L).getRole().getRoleId().intValue());
        assertEquals(1, userService.getUserById(11L).getRole().getRoleId().intValue());
        assertEquals(11, userService.getAll().getList().size());

        // get users
        RequestUser requestUser = new RequestUser();
        ResponseUser responseUser = userService.getUsers(requestUser);
        assertEquals(2, responseUser.getPagination().getTotalPages().intValue());
        assertEquals(11, responseUser.getPagination().getTotal().intValue());
        assertEquals(0, responseUser.getPagination().getCurrent().intValue());
        assertEquals(10, responseUser.getPagination().getPageSize().intValue());
        assertEquals(10, responseUser.getList().size());

        User userDto = new User();
        userDto.setName("admin");
        requestUser.setUser(userDto);
        responseUser = userService.getUsers(requestUser);
        assertEquals(1, responseUser.getPagination().getTotalPages().intValue());
        assertEquals(1, responseUser.getPagination().getTotal().intValue());
        assertEquals(0, responseUser.getPagination().getCurrent().intValue());
        assertEquals(10, responseUser.getPagination().getPageSize().intValue());
        assertEquals(1, responseUser.getList().size());

        userDto.setName("user");
        requestUser.setUser(userDto);
        responseUser = userService.getUsers(requestUser);
        assertEquals(1, responseUser.getPagination().getTotalPages().intValue());
        assertEquals(6, responseUser.getPagination().getTotal().intValue());
        assertEquals(0, responseUser.getPagination().getCurrent().intValue());
        assertEquals(10, responseUser.getPagination().getPageSize().intValue());
        assertEquals(6, responseUser.getList().size());

        User user7 = new User("user7", "", new Role(5L));
        User user8 = new User("user8", "", new Role(5L));
        User user9 = new User("user9", "", new Role(5L));
        User user10 = new User("user10", "", new Role(5L));
        User user11 = new User("user11", "", new Role(5L));
        userService.saveUsers(Arrays.asList(user7, user8, user9, user10, user11));
        responseUser = userService.getUsers(requestUser);
        assertEquals(2, responseUser.getPagination().getTotalPages().intValue());
        assertEquals(11, responseUser.getPagination().getTotal().intValue());
        assertEquals(0, responseUser.getPagination().getCurrent().intValue());
        assertEquals(10, responseUser.getPagination().getPageSize().intValue());
        assertEquals(10, responseUser.getList().size());

        requestUser.setPagination(new Pagination(1, 10));
        responseUser = userService.getUsers(requestUser);
        assertEquals(2, responseUser.getPagination().getTotalPages().intValue());
        assertEquals(11, responseUser.getPagination().getTotal().intValue());
        assertEquals(1, responseUser.getPagination().getCurrent().intValue());
        assertEquals(10, responseUser.getPagination().getPageSize().intValue());
        assertEquals(1, responseUser.getList().size());

        requestUser.setPagination(new Pagination(2, 3));
        responseUser = userService.getUsers(requestUser);
        assertEquals(4, responseUser.getPagination().getTotalPages().intValue());
        assertEquals(11, responseUser.getPagination().getTotal().intValue());
        assertEquals(2, responseUser.getPagination().getCurrent().intValue());
        assertEquals(3, responseUser.getPagination().getPageSize().intValue());
        assertEquals(3, responseUser.getList().size());

        userDto.setName("user");
        userDto.setRole(new Role(1L));
        requestUser.setPagination(new Pagination(1, 3));
        responseUser = userService.getUsers(requestUser);
        assertEquals(2, responseUser.getPagination().getTotalPages().intValue());
        assertEquals(4, responseUser.getPagination().getTotal().intValue());
        assertEquals(1, responseUser.getPagination().getCurrent().intValue());
        assertEquals(3, responseUser.getPagination().getPageSize().intValue());
        assertEquals(1, responseUser.getList().size());

        userDto.setRole(new Role(5L));
        requestUser.setPagination(new Pagination(1, 4));
        responseUser = userService.getUsers(requestUser);
        assertEquals(2, responseUser.getPagination().getTotalPages().intValue());
        assertEquals(5, responseUser.getPagination().getTotal().intValue());
        assertEquals(1, responseUser.getPagination().getCurrent().intValue());
        assertEquals(4, responseUser.getPagination().getPageSize().intValue());
        assertEquals(1, responseUser.getList().size());
    }

}
