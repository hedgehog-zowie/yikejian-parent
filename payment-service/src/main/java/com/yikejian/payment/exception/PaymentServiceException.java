package com.yikejian.payment.exception;

/**
 * <code>PaymentServiceException</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 14:19
 */
public class PaymentServiceException extends RuntimeException {

    private PaymentExceptionCode paymentExceptionCode;

    public PaymentServiceException(String message, PaymentExceptionCode paymentExceptionCode) {
        super(message);
        this.paymentExceptionCode = paymentExceptionCode;
    }

    public PaymentServiceException(String message) {
        super(message);
        this.paymentExceptionCode = PaymentExceptionCode.OTHER_ERROR;
    }

}
