package com.yikejian.user.service;

import com.yikejian.user.domain.role.Authority;
import com.yikejian.user.domain.role.Role;
import com.yikejian.user.domain.user.MyUserDetails;
import com.yikejian.user.domain.user.User;
import com.yikejian.user.repository.RoleRepository;
import com.yikejian.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author jackalope
 * @Title: UserService
 * @Package com.yikejian.user.service
 * @Description: TODO
 * @date 2017/12/21 22:34
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public void initUsers() {
        if (userRepository.count() == 0) {
            addDefaultAdmin();
            addDefaultUsers();
        }
    }

    private void addDefaultAdmin() {
        LOGGER.info("defining default roles");
        Role role1 = new Role(1L, "ADMIN_MANAGE", Authority.getAdminAuthority());
        Role role2 = new Role(2L, "SYSTEM_MANAGE", Authority.getSystemManageAuthority());
        Role role3 = new Role(3L, "INFO_MANAGE", Authority.getInfoManageAuthority());
        Role role4 = new Role(4L, "CUSTOMER_MANAGE", Authority.getCustomerManageAuthority());
        Role role5 = new Role(5L, "ORDER_MANAGE", Authority.getOrderManageAuthority());
        Role role6 = new Role(6L, "CUSTOMER", Authority.getCustomerAuthority());
        roleRepository.save(Arrays.asList(role1, role2, role3, role4, role5, role6));
        LOGGER.info("defining default users");
        User user1 = new User(1L, "admin", "admin", role1);
        User user2 = new User(2L, "system", "system", role2);
        User user3 = new User(3L, "info", "info", role3);
        User user4 = new User(4L, "customer", "customer", role4);
        User user5 = new User(5L, "order", "order", role5);
        userRepository.save(Arrays.asList(user1, user2, user3, user4, user5));
    }

    private void addDefaultUsers() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MyUserDetails(userRepository.findByName(username));
//        return new MyUserDetails(userRepository.findByNameAndEffectiveAndDeleted(username, 1, 0));
    }

}
