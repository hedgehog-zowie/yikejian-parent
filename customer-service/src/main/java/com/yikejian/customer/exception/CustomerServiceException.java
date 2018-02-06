package com.yikejian.customer.exception;

/**
 * <code>CustomerServiceException</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 14:19
 */
public class CustomerServiceException extends RuntimeException {

    private CustomerExceptionCode customerExceptionCode;

    public CustomerServiceException(CustomerExceptionCode customerExceptionCode,
                                     String message) {
        super(message);
        this.customerExceptionCode = customerExceptionCode;
    }

    public CustomerServiceException(String message) {
        super(message);
    }

    public CustomerExceptionCode getCustomerExceptionCode() {
        return customerExceptionCode;
    }

    public void setCustomerExceptionCode(CustomerExceptionCode customerExceptionCode) {
        this.customerExceptionCode = customerExceptionCode;
    }
}
