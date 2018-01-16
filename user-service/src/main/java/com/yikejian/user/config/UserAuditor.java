package com.yikejian.user.config;

import com.yikejian.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

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

    @Autowired
    public UserAuditor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getCurrentAuditor() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            return null;
        }
        if (securityContext.getAuthentication() == null) {
            return null;
        }
        if (securityContext.getAuthentication().getPrincipal() == null) {
            return null;
        }
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal.getClass().isAssignableFrom(Principal.class)) {
            return ((Principal) principal).getName();
        } else {
            return null;
        }
    }
}