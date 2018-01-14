package com.yikejian.customer;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class CustomerController {

//    @RequestMapping("/")
//    public Principal resource(Principal principal) {
//        return principal;
//    }

    /**
     * 需要客户查询权限
     *
     * @return
     */
    @PreAuthorize("hasAuthority('CUSTOMER_QUERY')")
    @RequestMapping("/query")
    public String queryCustomer(Principal principal) {
        return "fetch query customer, " + principal.getName();
    }

    /**
     * 需要客户编辑权限
     *
     * @return
     */
    @PreAuthorize("hasAuthority('CUSTOMER_EDIT')")
    @RequestMapping("/edit")
    public String editCustomer(Principal principal) {
        return "fetch edit customer, " + principal.getName();
    }

    /**
     * 需要用户角色权限
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/admin")
    public String helloAdmin(Principal principal) {
        return "hello " + principal.getName();
    }

    /**
     * 需要用户角色权限
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @RequestMapping("/employee")
    public String helloEmployee(Principal principal) {
        return "hello " + principal.getName();
    }

    /**
     * 需要用户角色权限
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @RequestMapping("manager")
    public String helloManager(Principal principal) {
        return "hello " + principal.getName();
    }

    /**
     * 需要用户角色权限
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("user")
    public String helloUser(Principal principal) {
        return "hello " + principal.getName();
    }

    /**
     * 需要用户角色权限
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_PUBLIC')")
    @RequestMapping("public")
    public String helloPublic(Principal principal) {
        return "hello public : " + principal;
    }

    /**
     * 需要用户角色权限
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_TRUSTED_CLIENT')")
    @RequestMapping("trust")
    public String helloTrusted(Principal principal) {
        return "hello trusted : " + principal.getName();
    }

}
