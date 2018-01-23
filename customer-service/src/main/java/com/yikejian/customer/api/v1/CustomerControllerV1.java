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
@RequestMapping(path = "/v1")
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

}
