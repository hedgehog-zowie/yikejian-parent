package com.yikejian.user.config;

import com.yikejian.user.service.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <code>SecurityConfig</code>.
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
    private MyUserDetailsService myUserDetailsService;
//    @Autowired
//    private TokenEndpoint tokenEndpoint;
//
//    @PostConstruct
//    public void reconfigure() {
//        Set<HttpMethod> allowedMethods =
//                new HashSet<>(Arrays.asList(HttpMethod.GET, HttpMethod.POST));
//        tokenEndpoint.setAllowedRequestMethods(allowedMethods);
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
//                .exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint)
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin()
//                .requestMatchers().antMatchers(
//                        "/oauth/authorize", "/oauth/confirm_access", "/oauth/token")
//                .and()
                .httpBasic().disable()
                .anonymous().disable()
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated();
    }

    @Override
    @Autowired // <-- This is crucial otherwise Spring Boot creates its own
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        myUserDetailsService.initUsers();
        authenticationManagerBuilder.userDetailsService(myUserDetailsService);
    }

}
