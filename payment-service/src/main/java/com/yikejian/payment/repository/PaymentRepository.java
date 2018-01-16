package com.yikejian.payment.repository;

import com.yikejian.payment.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>PaymentRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

    Payment findByPaymentId(Long paymentId);

}
