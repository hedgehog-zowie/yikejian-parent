package com.yikejian.payment.api.v1;

import com.yikejian.payment.api.v1.dto.RequestPayment;
import com.yikejian.payment.domain.payment.Payment;
import com.yikejian.payment.exception.PaymentServiceException;
import com.yikejian.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * <code>PaymentControllerV1</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:57
 */
@RestController
public class PaymentControllerV1 {

    private PaymentService paymentService;

    @Autowired
    public PaymentControllerV1(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "/payment/{payment_id}", method = RequestMethod.GET)
    public ResponseEntity getPayments(final @PathVariable(value = "payment_id") Long paymentId) {
        // todo send log
        return Optional.ofNullable(paymentService.getPaymentById(paymentId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new PaymentServiceException("Not found payment."));
    }

    @PostMapping("/payment")
    public ResponseEntity addPayment(final Payment payment) {
        // todo send log
        return Optional.ofNullable(paymentService.savePayment(payment))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new PaymentServiceException("Not found payment."));
    }

    @PutMapping("/payment")
    public ResponseEntity updatePayment(final Payment payment) {
        // todo send log
        return Optional.ofNullable(paymentService.savePayment(payment))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new PaymentServiceException("Not found payment."));
    }

    @GetMapping("/payments")
    public ResponseEntity getPayments(final RequestPayment requestPayment) {
        // todo send log
        return Optional.ofNullable(paymentService.getPayments(requestPayment))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new PaymentServiceException("Not found any payment."));
    }

}
