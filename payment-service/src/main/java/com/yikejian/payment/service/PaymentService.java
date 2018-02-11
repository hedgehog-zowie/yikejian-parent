package com.yikejian.payment.service;

import com.github.wxpay.sdk.WXPay;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.payment.api.v1.dto.Pagination;
import com.yikejian.payment.api.v1.dto.RequestPayment;
import com.yikejian.payment.api.v1.dto.ResponsePayment;
import com.yikejian.payment.config.MyWechatPayConfig;
import com.yikejian.payment.domain.customer.Account;
import com.yikejian.payment.domain.customer.Customer;
import com.yikejian.payment.domain.order.Order;
import com.yikejian.payment.domain.payment.Payment;
import com.yikejian.payment.domain.payment.PaymentStatus;
import com.yikejian.payment.domain.payment.PaymentType;
import com.yikejian.payment.exception.PaymentExceptionCode;
import com.yikejian.payment.exception.PaymentServiceException;
import com.yikejian.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
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
    private OAuth2RestTemplate oAuth2RestTemplate;

    private WXPay wxpay;
    @Value("${yikejian.customer.api.url}")
    private String customerApi;
    @Value("${yikejian.customer.api.url}")
    private String updateCustomerApi;
    @Value("${yikejian.order.api.url}")
    private String orderApi;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository,
                          OAuth2RestTemplate oAuth2RestTemplate) throws Exception {
        this.wxpay = new WXPay(new MyWechatPayConfig());
        this.paymentRepository = paymentRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @HystrixCommand
    @Transactional
    public Payment savePayment(Payment payment) {
        switch (payment.getPaymentType()) {
            case ACCOUNT:
                Customer customer = oAuth2RestTemplate.getForObject(customerApi, Customer.class);
                if (customer == null) {
                    throw new PaymentServiceException("未知的客户", PaymentExceptionCode.UNKNOWN_CUSTOMER);
                }
                Account account = customer.getAccount();
                if (account == null) {
                    throw new PaymentServiceException("未知的账户", PaymentExceptionCode.UNKNOWN_ACCOUT);
                }
                Order order = oAuth2RestTemplate.getForObject(orderApi, Order.class);
                if (order == null) {
                    throw new PaymentServiceException("未知的订单", PaymentExceptionCode.UNKNOWN_ORDER);
                }
                if (!order.getActualAmount().equals(payment.getAmount())) {
                    throw new PaymentServiceException("支付金额与订单实际应付金额不匹配", PaymentExceptionCode.MONEY_DIFFER);
                }
                if (account.getBalance() < payment.getAmount()) {
                    throw new PaymentServiceException("账户余额不足", PaymentExceptionCode.INSUFFICIENT_BALANCE);
                }
                account.setBalance(account.getBalance() - payment.getAmount());
                oAuth2RestTemplate.put(updateCustomerApi, customer);
                // TODO: 2018/2/11
                break;
            case WECHAT:
                break;
            default:
                throw new PaymentServiceException("未知的支付方式，仅支持账户余额和微信支付", PaymentExceptionCode.UNKNOWN_PAY_TYPE);
        }
        return paymentRepository.save(payment);
    }

    private void wechatPay() {

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
