package com.yikejian.user.repository;

import com.yikejian.user.UserServiceApplication;
import com.yikejian.user.domain.role.Role;
import com.yikejian.user.domain.user.User;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author jackalope
 * @Title: UserRepositoryTest
 * @Package com.yikejian.user.repository
 * @Description: TODO
 * @date 2018/1/14 22:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
//@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeClass
    public static void beforeClass() {
    }

    @AfterClass
    public static void afterClass() {
    }

    @Before
    public void before() {
        Role role1 = new Role("admin1", "auth1, auth2, auth3");
        Role role2 = new Role("admin2", "auth1, auth2, auth3, auth4, auth5, auth6");
        Role role3 = new Role("admin3", "auth4, auth5, auth6");
        roleRepository.save(Arrays.asList(role1, role2, role3));
        User user1 = new User("user1", "password1", role1);
        User user2 = new User("user2", "password2", role2);
        User user3 = new User("user3", "password3", role3);
        User user4 = new User("user4", "password4", role1);
        userRepository.save(Arrays.asList(user1, user2, user3, user4));
    }

    @After
    public void after() {
        User user = userRepository.findById(8L);
        userRepository.delete(user);
        Role role = roleRepository.findByRoleId(8L);
        roleRepository.delete(role);
    }

    @Test
    public void findAll() {
        assertThat(roleRepository.findAll()).hasSize(8);
        assertThat(userRepository.findAll()).hasSize(9);
    }

    @Test
    public void findById() {
        assertEquals(roleRepository.findByRoleId(6L).getRoleName(), "admin1");
        assertEquals(userRepository.findById(6L).getName(), "user1");
    }

    @Test
    public void save() {
        Role role = roleRepository.findByRoleId(6L);
        role.setRoleName("asdf");
        roleRepository.save(role);
        assertEquals(roleRepository.findByRoleId(6L).getRoleName(), "asdf");

        User user = userRepository.findById(8L);
        assertEquals(user.getRole().getRoleName(), "admin3");
        user.setRole(role);
        assertEquals(userRepository.save(user).getRole().getRoleName(), "asdf");

        Role role2 = new Role(1L);
        user.setRole(role2);
        assertEquals(userRepository.save(user).getRole().getRoleName(), "ADMIN");

        List<User> userList = userRepository.findByRole(role);
        assertEquals(userList.size(), 2);

        assertThat(roleRepository.findAll()).hasSize(8);
        assertThat(userRepository.findAll()).hasSize(9);
    }

}
