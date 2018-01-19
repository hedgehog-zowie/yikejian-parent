package com.yikejian.payment.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.payment.api.v1.dto.Pagination;
import com.yikejian.payment.api.v1.dto.RequestPayment;
import com.yikejian.payment.api.v1.dto.ResponsePayment;
import com.yikejian.payment.domain.payment.Payment;
import com.yikejian.payment.domain.payment.PaymentStatus;
import com.yikejian.payment.domain.payment.PaymentType;
import com.yikejian.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>PaymentService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @HystrixCommand
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @HystrixCommand
    public List<Payment> savePayments(List<Payment> paymentList) {
        return (List<Payment>) paymentRepository.save(paymentList);
    }

    @HystrixCommand
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findByPaymentId(paymentId);
    }

    @HystrixCommand
    public ResponsePayment getAll() {
        return new ResponsePayment((List<Payment>) paymentRepository.findAll());
    }

    @HystrixCommand
    public ResponsePayment getPayments(RequestPayment requestPayment) {
        Pagination pagination;
        if (requestPayment != null && requestPayment.getPagination() != null) {
            pagination = requestPayment.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestPayment != null && requestPayment.getSort() != null) {
            sort = new Sort(requestPayment.getSort().getDirection(), requestPayment.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<Payment> page = paymentRepository.findAll(paymentSpec(requestPayment.getPayment()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());

        return new ResponsePayment(page.getContent(), pagination);
    }

    private Specification<Payment> paymentSpec(final Payment payment) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (payment != null) {
                if (payment.getPaymentStatus() != null) {
                    predicateList.add(cb.equal(root.get("paymentStatus").as(PaymentStatus.class), payment.getPaymentStatus()));
                }
                if (payment.getPaymentType() != null) {
                    predicateList.add(cb.equal(root.get("paymentStatus").as(PaymentType.class), payment.getPaymentType()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
