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

    private List<Customer> list;
    private Pagination pagination;

    public ResponseCustomer(List<Customer> list) {
        this.list = list;
    }

    public ResponseCustomer(List<Customer> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<Customer> getList() {
        return list;
    }

    public void setList(List<Customer> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
