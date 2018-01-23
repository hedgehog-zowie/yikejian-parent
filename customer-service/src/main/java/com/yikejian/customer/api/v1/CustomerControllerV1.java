package com.yikejian.customer.api.v1;

import com.yikejian.customer.api.v1.dto.RequestCustomer;
import com.yikejian.customer.domain.customer.Customer;
import com.yikejian.customer.exception.CustomerServiceException;
import com.yikejian.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
public class CustomerControllerV1 {

    private CustomerService customerService;

    @Autowired
    public CustomerControllerV1(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/customer/{customer_id}", method = RequestMethod.GET)
    public ResponseEntity getCustomers(final @PathVariable(value = "customer_id") Long customerId) {
        // todo send log
        return Optional.ofNullable(customerService.getCustomerById(customerId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found customer."));
    }

    @PostMapping("/customer")
    public ResponseEntity addCustomer(final Customer customer) {
        // todo send log
        return Optional.ofNullable(customerService.saveCustomer(customer))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found customer."));
    }

    @PutMapping("/customer")
    public ResponseEntity updateCustomer(final Customer customer) {
        // todo send log
        return Optional.ofNullable(customerService.saveCustomer(customer))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found customer."));
    }

    @GetMapping("/customers")
    public ResponseEntity getCustomers(final RequestCustomer requestCustomer) {
        // todo send log
        return Optional.ofNullable(customerService.getCustomers(requestCustomer))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found any customer."));
    }
    
    /**
     * 需要客户查询权限
     *
     * @return
     */
    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    @RequestMapping("/query")
    public String queryCustomer(Principal principal) {
        return "fetch query customer, " + principal.getName();
    }

    /**
     * 需要客户编辑权限
     *
     * @return
     */
    @PreAuthorize("hasAuthority('CUSTOMER_WRITE')")
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

    @GetMapping("token")
    public String getToken(){
        System.out.println("use code get openid and session_key from weixin, generate the token of myself.");
        return "use code get openid and session_key from weixin, generate the token of myself.";
    }

}
