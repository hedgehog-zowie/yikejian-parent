package com.yikejian.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <code>UserController</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/12/20 11:52
 */

@RestController
@PreAuthorize("hasRole('USER')")
public class UserController {

    @RequestMapping("/")
    public Principal resource(Principal principal) {
        return principal;
    }

}
