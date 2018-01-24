package com.yikejian.customer.config;

import com.yikejian.customer.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

/**
 * <code>UserAuditorBean</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 14:13
 */
@Configuration
public class UserAuditor implements AuditorAware<String> {

    private OAuth2RestTemplate oAuth2RestTemplate;

    private final String DEFAULT = "sys";

    @Value("${yikejian.user.api.url}")
    private String userApi;

    @Autowired
    public UserAuditor(OAuth2RestTemplate oAuth2RestTemplate) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @Override
    public String getCurrentAuditor() {
//        User user = oAuth2RestTemplate.getForObject(userApi, User.class);
//        if (user != null) {
//            return user.getName();
//        } else {
//            return null;
//        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            return DEFAULT;
        }
        if (securityContext.getAuthentication() == null) {
            return DEFAULT;
        }
        if (securityContext.getAuthentication().getPrincipal() == null) {
            return DEFAULT;
        }
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal.getClass().isAssignableFrom(String.class)) {
            return (String) principal;
        } else {
            return DEFAULT;
        }
    }
}
