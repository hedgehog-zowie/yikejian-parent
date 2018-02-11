package com.yikejian.payment.domain.customer;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author jackalope
 * @Title: Account
 * @Package com.yikejian.customer.domain.customer
 * @Description: TODO
 * @date 2018/1/23 21:57
 */
public class Account {

    @Id
    @GeneratedValue
    private Long accountId;

    /**
     * 账户余额
     */
    private Double balance;
    /**
     * 总消费额
     */
    private Double amount;
    /**
     * 积分
     */
    private Integer point;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
