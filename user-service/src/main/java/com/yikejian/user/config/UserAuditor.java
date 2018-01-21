package com.yikejian.user.config;

import com.yikejian.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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

    private UserService userService;

    private final String DEFAULT = "sys";

    @Autowired
    public UserAuditor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getCurrentAuditor() {
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
