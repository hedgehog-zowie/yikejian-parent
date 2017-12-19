package com.yikejian.user.secure;

import com.yikejian.user.entity.Role;
import com.yikejian.user.entity.User;
import com.yikejian.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;

/**
 * <code>UserWebSecurityConfigurerAdapter</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/12/19 17:01
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin()
                .and()
                .httpBasic().disable()
                .anonymous().disable()
                .authorizeRequests().anyRequest().authenticated();
    }

    @Override
    @Autowired // <-- This is crucial otherwise Spring Boot creates its own
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        if (userRepository.count() == 0) {
//            LOGGER.info("Defining (3 users)");
//            userRepository.save(new User("user", "password", Arrays.asList(new Role("USER"))));
//            userRepository.save(new User("admin", "admin", Arrays.asList(new Role("ADMIN"))));
//            userRepository.save(new User("employee", "password", Arrays.asList(new Role("EMPLOYEE"))));
//            userRepository.save(new User("manager", "password", Arrays.asList(new Role("EMPLOYEE"), new Role("MANAGER"))));
//        }
//        authenticationManagerBuilder.userDetailsService(userDetailsService(userRepository));
        authenticationManagerBuilder
                .inMemoryAuthentication()

                .withUser("user").password("password")
                .roles("USER")

                .and()

                .withUser("admin").password("password")
                .roles("USER", "ADMIN")
        ;
    }

    @Bean
    protected UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> new CustomUserDetails(userRepository.findByUsername(username));
    }

}
