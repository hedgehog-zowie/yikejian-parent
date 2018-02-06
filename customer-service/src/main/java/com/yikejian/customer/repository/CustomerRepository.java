package com.yikejian.customer.repository;

import com.yikejian.customer.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>CustomerRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    Customer findByCustomerId(Long customerId);

    Customer findByCustomerName(String customerName);

    Customer findByOpenId(String openId);

    Customer findByEffectiveAndDeleted(Integer effective, Integer deleted);

}
