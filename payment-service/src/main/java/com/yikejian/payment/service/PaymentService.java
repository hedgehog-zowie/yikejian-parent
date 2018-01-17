package com.yikejian.payment.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.payment.api.v1.dto.Pagination;
import com.yikejian.payment.api.v1.dto.PaymentDto;
import com.yikejian.payment.api.v1.dto.RequestPaymentDto;
import com.yikejian.payment.api.v1.dto.ResponsePaymentDto;
import com.yikejian.payment.domain.payment.Payment;
import com.yikejian.payment.repository.PaymentRepository;
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
    public Payment savePayment(PaymentDto paymentDto) {
        Payment payment;
        if (paymentDto.getPaymentId() != null) {
            payment = paymentRepository.findByPaymentId(paymentDto.getPaymentId());
        } else {
            payment = new Payment();
        }
        payment.fromPaymentDto(paymentDto);
        return paymentRepository.save(payment);
    }

    @HystrixCommand
    public List<Payment> savePayments(List<PaymentDto> paymentDtoList) {
        List<Payment> paymentList = Lists.newArrayList();
        for (PaymentDto paymentDto : paymentDtoList) {
            Payment payment;
            if (paymentDto.getPaymentId() != null) {
                payment = paymentRepository.findByPaymentId(paymentDto.getPaymentId());
            } else {
                payment = new Payment();
            }
            payment.fromPaymentDto(paymentDto);
            paymentList.add(payment);
        }
        return (List<Payment>) paymentRepository.save(paymentList);
    }

    @HystrixCommand
    public PaymentDto getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId);
        return payment.toPaymentDto();
    }

    @HystrixCommand
    public ResponsePaymentDto getAll() {
        List<Payment> paymentList = (List<Payment>) paymentRepository.findAll();
        List<PaymentDto> paymentDtoList = Lists.newArrayList(
                paymentList.stream().map(Payment::toPaymentDto).collect(Collectors.toList())
        );
        return new ResponsePaymentDto(paymentDtoList);
    }

    @HystrixCommand
    public ResponsePaymentDto getPayments(RequestPaymentDto requestPaymentDto) {
        Pagination pagination;
        if (requestPaymentDto != null && requestPaymentDto.getPagination() != null) {
            pagination = requestPaymentDto.getPagination();
        } else {
            pagination = new Pagination();
        }

        Sort sort = null;
        if (requestPaymentDto != null && requestPaymentDto.getSort() != null) {
            sort = new Sort(requestPaymentDto.getSort().getDirection(), requestPaymentDto.getSort().getField());
        }

        PageRequest pageRequest = new PageRequest(
                pagination.getCurrentPage(),
                pagination.getPageSize(),
                sort);
        Page<Payment> page = paymentRepository.findAll(paymentSpec(requestPaymentDto.getPayment()), pageRequest);

        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalSize(page.getTotalElements());
        List<PaymentDto> paymentDtoList = Lists.newArrayList(page.getContent().stream().
                map(Payment::toPaymentDto).collect(Collectors.toList()));
        return new ResponsePaymentDto(paymentDtoList, pagination);
    }

    private Specification<Payment> paymentSpec(final PaymentDto paymentDto) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (paymentDto != null) {
                if (StringUtils.isNotBlank(paymentDto.getPaymentName())) {
                    predicateList.add(cb.like(root.get("paymentName").as(String.class), "%" + paymentDto.getPaymentName() + "%"));
                }
                if (paymentDto.getStartTime() != null) {
                    predicateList.add(cb.equal(root.get("startTime").as(Integer.class), paymentDto.getStartTime()));
                }
                if (paymentDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("endTime").as(Integer.class), paymentDto.getEndTime()));
                }
                if (paymentDto.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), paymentDto.getEffective()));
                }
                if (paymentDto.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), paymentDto.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }

}
