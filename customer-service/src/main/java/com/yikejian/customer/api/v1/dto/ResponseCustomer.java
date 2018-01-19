package com.yikejian.customer.api.v1.dto;

import com.yikejian.customer.domain.customer.Customer;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseCustomer {

    private List<Customer> customerList;
    private Pagination pagination;

    public ResponseCustomer(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public ResponseCustomer(List<Customer> customerList, Pagination pagination) {
        this.customerList = customerList;
        this.pagination = pagination;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
