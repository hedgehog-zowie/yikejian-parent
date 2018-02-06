package com.yikejian.customer.api.v1;

import com.yikejian.customer.api.v1.dto.Pagination;
import com.yikejian.customer.api.v1.dto.RequestCustomer;
import com.yikejian.customer.domain.customer.Customer;
import com.yikejian.customer.exception.CustomerServiceException;
import com.yikejian.customer.service.CustomerService;
import com.yikejian.customer.service.WechatLoginService;
import com.yikejian.customer.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1")
public class CustomerControllerV1 {

    private CustomerService customerService;
    private WechatLoginService wechatLoginService;

    @Autowired
    public CustomerControllerV1(CustomerService customerService,
                                WechatLoginService wechatLoginService) {
        this.customerService = customerService;
        this.wechatLoginService = wechatLoginService;
    }

    @PostMapping("/customer")
    public ResponseEntity addCustomer(@RequestBody final Customer customer) {
        customer.setCustomerId(null);
        // todo send log
        return Optional.ofNullable(customerService.saveCustomer(customer))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found customer."));
    }

    @PutMapping("/customer")
    public ResponseEntity updateCustomer(@RequestBody final Customer customer) {
        // todo send log
        return Optional.ofNullable(customerService.saveCustomer(customer))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found customer."));
    }

    @GetMapping("/customers")
    public ResponseEntity getCustomers(final @RequestParam(value = "params", required = false) String params) {
        RequestCustomer requestCustomer;
        if (StringUtils.isBlank(params)) {
            requestCustomer = new RequestCustomer(new Customer(), new Pagination(), null);
        } else {
            try {
                requestCustomer = JsonUtils.fromJson(URLDecoder.decode(params, "UTF-8"), RequestCustomer.class);
            } catch (UnsupportedEncodingException e) {
                throw new CustomerServiceException(e.getLocalizedMessage());
            }
        }
        requestCustomer.getCustomer().setDeleted(0);
        // todo send log
        return Optional.ofNullable(customerService.getCustomers(requestCustomer))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found any customer."));
    }

    @RequestMapping(value = "/customer/{customer_id}", method = RequestMethod.GET)
    public ResponseEntity getCustomers(final @PathVariable(value = "customer_id") Long customerId) {
        // todo send log
        return Optional.ofNullable(customerService.getCustomerById(customerId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new CustomerServiceException("Not found customer."));
    }

    @GetMapping("/me")
    public ResponseEntity me(Principal principal) {
        Customer customer = null;
        if (principal != null) {
            customer = customerService.getCustomerByCustomerName(principal.getName());
        }
        return Optional.ofNullable(customer)
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UsernameNotFoundException("customer not found"));
    }

    @GetMapping("/login")
    public ResponseEntity<OAuth2AccessToken> login(final @RequestParam(value = "code") String code) {
        return Optional.ofNullable(wechatLoginService.login(code))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UsernameNotFoundException("login failed."));
    }

}
