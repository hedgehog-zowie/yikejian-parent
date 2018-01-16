package com.yikejian.customer.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.customer.api.v1.dto.CustomerDto;
import com.yikejian.customer.api.v1.dto.Pagination;
import com.yikejian.customer.api.v1.dto.RequestCustomerDto;
import com.yikejian.customer.api.v1.dto.ResponseCustomerDto;
import com.yikejian.customer.domain.customer.Customer;
import com.yikejian.customer.repository.CustomerRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code>CustomerService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @HystrixCommand
    public Customer saveCustomer(CustomerDto customerDto) {
        Customer customer;
        if (customerDto.getCustomerId() != null) {
            customer = customerRepository.findByCustomerId(customerDto.getCustomerId());
        } else {
            customer = new Customer();
        }
        customer.fromCustomerDto(customerDto);
        return customerRepository.save(customer);
    }

    @HystrixCommand
    public List<Customer> saveCustomers(List<CustomerDto> customerDtoList) {
        List<Customer> customerList = Lists.newArrayList();
        for (CustomerDto customerDto : customerDtoList) {
            Customer customer;
            if (customerDto.getCustomerId() != null) {
                customer = customerRepository.findByCustomerId(customerDto.getCustomerId());
            } else {
                customer = new Customer();
            }
            customer.fromCustomerDto(customerDto);
            customerList.add(customer);
        }
        return (List<Customer>) customerRepository.save(customerList);
    }

    @HystrixCommand
    public CustomerDto getCustomerById(Long customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId);
        return customer.toCustomerDto();
    }

    @HystrixCommand
    public ResponseCustomerDto getAll() {
        List<Customer> customerList = (List<Customer>) customerRepository.findAll();
        List<CustomerDto> customerDtoList = Lists.newArrayList(
                customerList.stream().map(Customer::toCustomerDto).collect(Collectors.toList())
        );
        return new ResponseCustomerDto(customerDtoList);
    }

    @HystrixCommand
    public ResponseCustomerDto getCustomers(RequestCustomerDto requestCustomerDto) {
        Pagination pagination;
        if (requestCustomerDto != null && requestCustomerDto.getPagination() != null) {
            pagination = requestCustomerDto.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestCustomerDto != null && requestCustomerDto.getSort() != null) {
            sort = new Sort(requestCustomerDto.getSort().getDirection(), requestCustomerDto.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<Customer> page = customerRepository.findAll(customerSpec(requestCustomerDto.getCustomer()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());
        List<CustomerDto> customerDtoList = Lists.newArrayList(page.getContent().stream().
                map(Customer::toCustomerDto).collect(Collectors.toList()));
        return new ResponseCustomerDto(customerDtoList, pagination);
    }

    private Specification<Customer> customerSpec(final CustomerDto customerDto) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (customerDto != null) {
                if (StringUtils.isNotBlank(customerDto.getCustomerName())) {
                    predicateList.add(cb.like(root.get("customerName").as(String.class), "%" + customerDto.getCustomerName() + "%"));
                }
                if (customerDto.getStartTime() != null) {
                    predicateList.add(cb.equal(root.get("startTime").as(Integer.class), customerDto.getStartTime()));
                }
                if (customerDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("endTime").as(Integer.class), customerDto.getEndTime()));
                }
                if (customerDto.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), customerDto.getEffective()));
                }
                if (customerDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), customerDto.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
