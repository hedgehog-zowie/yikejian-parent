package com.yikejian.customer.service;

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
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

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
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @HystrixCommand
    public List<Customer> saveCustomers(List<Customer> customerList) {
        return (List<Customer>) customerRepository.save(customerList);
    }

    @HystrixCommand
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findByCustomerId(customerId);
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

        Sort sort = null;
        if (requestCustomer != null && requestCustomer.getSort() != null) {
            sort = new Sort(requestCustomer.getSort().getDirection(), requestCustomer.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<Customer> page = customerRepository.findAll(customerSpec(requestCustomer.getCustomer()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());

        return new ResponseCustomer(page.getContent(), pagination);
    }

    private Specification<Customer> customerSpec(final Customer customer) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (customer != null) {
                if (StringUtils.isNotBlank(customer.getCustomerName())) {
                    predicateList.add(cb.like(root.get("customerName").as(String.class), "%" + customer.getCustomerName() + "%"));
                }
                if (customer.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), customer.getDeleted()));
                }
                if (customer.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), customer.getEffective()));
                }
                if (customer.getTitle() != null && customer.getTitle().getTitleId() != null) {
                    Join<Customer, Title> titleJoin = root.join(root.getModel().getSingularAttribute("title", Title.class), JoinType.LEFT);
                    predicateList.add(cb.equal(titleJoin.get("titleId").as(Long.class), customer.getTitle().getTitleId()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
