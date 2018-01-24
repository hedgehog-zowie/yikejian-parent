package com.yikejian.customer.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yikejian.customer.domain.BaseEntity;
import com.yikejian.customer.domain.customer.Customer;

import javax.persistence.*;
import java.util.Set;

/**
 * @author jackalope
 * @Title: Account
 * @Package com.yikejian.customer.domain.customer
 * @Description: TODO
 * @date 2018/1/23 21:57
 */
@Entity
public class Account extends BaseEntity {

    @Id
    @GeneratedValue
    private Long accountId;

    /**
     * 账户余额
     */
    private Double balance;
    /**
     * 积分
     */
    private Integer point;
    /**
     * 关联的客户
     */
    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<Customer> customerSet;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Set<Customer> getCustomerSet() {
        return customerSet;
    }

    public void setCustomerSet(Set<Customer> customerSet) {
        this.customerSet = customerSet;
    }
}