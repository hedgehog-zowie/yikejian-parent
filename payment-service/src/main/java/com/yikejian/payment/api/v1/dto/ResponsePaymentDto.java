package com.yikejian.payment.api.vi.dto;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponsePaymentDto {

    private List<PaymentDto> paymentList;
    private Pagination pagination;

    public ResponsePaymentDto(List<PaymentDto> paymentList) {
        this.paymentList = paymentList;
    }

    public ResponsePaymentDto(List<PaymentDto> paymentList, Pagination pagination) {
        this.paymentList = paymentList;
        this.pagination = pagination;
    }

    public List<PaymentDto> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<PaymentDto> paymentList) {
        this.paymentList = paymentList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
