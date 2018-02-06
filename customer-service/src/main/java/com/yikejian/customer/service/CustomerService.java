package com.yikejian.customer.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.customer.api.v1.dto.Pagination;
import com.yikejian.customer.api.v1.dto.RequestCustomer;
import com.yikejian.customer.api.v1.dto.ResponseCustomer;
import com.yikejian.customer.domain.customer.Customer;
import com.yikejian.customer.domain.title.Title;
import com.yikejian.customer.repository.CustomerRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
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

    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public CustomerService(CustomerRepository customerRepository
    ) {
        this.customerRepository = customerRepository;
    }

    @HystrixCommand
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(transform(customer));
    }

    @HystrixCommand
    public List<Customer> saveCustomers(List<Customer> customerList) {
        return (List<Customer>) customerRepository.save(
                Lists.newArrayList(customerList.stream().
                        map(this::transform).collect(Collectors.toList()))
        );
    }

    private Customer transform(Customer customer) {
        Customer newCustomer = customer;
        if (customer.getCustomerId() != null) {
            Customer oldCustomer = customerRepository.findByCustomerId(customer.getCustomerId());
            newCustomer = oldCustomer.mergeOther(customer);
        }
        return newCustomer;
    }

    @HystrixCommand
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    @HystrixCommand
    public Customer getCustomerByCustomerName(String customerName) {
        return customerRepository.findByCustomerName(customerName);
    }

    @HystrixCommand
    public Customer getCustomerByOpenId(String openId) {
        return customerRepository.findByOpenId(openId);
    }

    @HystrixCommand
    public ResponseCustomer getAll() {
        return new ResponseCustomer((List<Customer>) customerRepository.findAll());
    }

    @HystrixCommand
    public ResponseCustomer getCustomers(RequestCustomer requestCustomer) {
        Pagination pagination;
        if (requestCustomer != null && requestCustomer.getPagination() != null) {
            pagination = requestCustomer.getPagination();
        } else {
            pagination = new Pagination();
        }

        String filed = "lastModifiedAt";
        Sort.Direction direction = Sort.Direction.DESC;
        if (requestCustomer != null && requestCustomer.getSorter() != null) {
            if (requestCustomer.getSorter().getField() != null) {
                filed = requestCustomer.getSorter().getField();
            }
            if ("ascend".equals(requestCustomer.getSorter().getOrder())) {
                direction = Sort.Direction.ASC;
            }
        }
        Sort sort = new Sort(direction, filed);

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrent() - 1,
                pagination.getPageSize(),
                sort);
        Page<Customer> page = customerRepository.findAll(customerSpec(requestCustomer.getCustomer()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotal(page.getTotalElements());

        return new ResponseCustomer(page.getContent(), pagination);
    }

    private Specification<Customer> customerSpec(final Customer customer) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (customer != null) {
                if (StringUtils.isNotBlank(customer.getCustomerName())) {
                    predicateList.add(cb.like(root.get("customerName").as(String.class), "%" + customer.getCustomerName() + "%"));
                }
                if (StringUtils.isNotBlank(customer.getMobileNumber())) {
                    predicateList.add(cb.like(root.get("mobileNumber").as(String.class), "%" + customer.getMobileNumber() + "%"));
                }
                if (customer.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), customer.getDeleted()));
                }
                if (customer.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), customer.getEffective()));
                }
                if (customer.getTitle() != null && customer.getTitle().getTitleId() != null) {
                    Join<Customer, Title> join = root.join("title");
                    predicateList.add(cb.equal(join.<Long>get("titleId"), customer.getTitle().getTitleId()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
