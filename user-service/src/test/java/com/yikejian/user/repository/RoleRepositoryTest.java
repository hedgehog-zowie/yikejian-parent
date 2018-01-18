package com.yikejian.user.repository;

import com.yikejian.user.UserServiceApplication;
import com.yikejian.user.domain.role.Role;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author jackalope
 * @Title: RoleRepositoryTest
 * @Package com.yikejian.user.repository
 * @Description: TODO
 * @date 2018/1/14 22:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
//@RunWith(SpringRunner.class)
//@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

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
    }

    @After
    public void after() {
        Role role = roleRepository.findByRoleId(8L);
        roleRepository.delete(role);
    }

    @Test
    public void findAll() {
        assertThat(roleRepository.findAll()).hasSize(8);
    }

    @Test
    @Transactional
    public void findById() {
        Role role = roleRepository.findByRoleId(1L);
        assertEquals("ADMIN", role.getRoleName());
        assertEquals(1, role.getUserSet().size());
    }

    @Test
    public void save() {
        Role role = roleRepository.findByRoleId(6L);
        role.setRoleName("asdf");
        roleRepository.save(role);
        assertThat(roleRepository.findAll()).hasSize(8);
        assertEquals(roleRepository.findByRoleId(6L).getRoleName(), "asdf");
    }

}
