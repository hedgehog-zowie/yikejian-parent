package com.yikejian.customer.api.v1.dto;

import com.yikejian.customer.domain.customer.Customer;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestCustomer {

    private Customer customer;
    private Pagination pagination;
    private Sort sorter;

    public RequestCustomer(Customer customer, Pagination pagination, Sort sorter) {
        this.customer = customer;
        this.pagination = pagination;
        this.sorter = sorter;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Sort getSorter() {
        return sorter;
    }

    public void setSorter(Sort sorter) {
        this.sorter = sorter;
    }
}
