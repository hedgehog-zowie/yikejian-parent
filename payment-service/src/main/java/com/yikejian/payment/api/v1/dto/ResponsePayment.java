package com.yikejian.payment.api.v1.dto;

import com.yikejian.payment.domain.payment.Payment;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponsePayment {

    private List<Payment> paymentList;
    private Pagination pagination;

    public ResponsePayment(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public ResponsePayment(List<Payment> paymentList, Pagination pagination) {
        this.paymentList = paymentList;
        this.pagination = pagination;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
