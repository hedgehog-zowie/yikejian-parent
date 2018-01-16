package com.yikejian.customer.api.v1.dto;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseCustomerDto {

    private List<CustomerDto> customerList;
    private Pagination pagination;

    public ResponseCustomerDto(List<CustomerDto> customerList) {
        this.customerList = customerList;
    }

    public ResponseCustomerDto(List<CustomerDto> customerList, Pagination pagination) {
        this.customerList = customerList;
        this.pagination = pagination;
    }

    public List<CustomerDto> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerDto> customerList) {
        this.customerList = customerList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
