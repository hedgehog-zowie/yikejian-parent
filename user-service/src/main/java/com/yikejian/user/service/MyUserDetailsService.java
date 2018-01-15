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
        LOGGER.info("defining default admins");
        Role role1 = new Role("ADMIN", Authority.getAdminAuthority());
        Role role2 = new Role("SYSTEM", Authority.getSystemAuthority());
        Role role3 = new Role("INFO", Authority.getInfoAuthority());
        Role role4 = new Role("CUSTOMER", Authority.getCustomerAuthority());
        Role role5 = new Role("ORDER", Authority.getOrderAuthority());
        roleRepository.save(Arrays.asList(role1, role2, role3, role4, role5));
        User user1 = new User("admin", "admin", role1);
        User user2 = new User("system", "system", role2);
        User user3 = new User("info", "info", role3);
        User user4 = new User("customer", "customer", role4);
        User user5 = new User("order", "order", role5);
        userRepository.save(Arrays.asList(user1, user2, user3, user4, user5));
    }

    private void addDefaultUsers() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MyUserDetails(userRepository.findByUserName(username));
    }

}
