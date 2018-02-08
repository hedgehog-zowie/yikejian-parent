package com.yikejian.customer.domain.customer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yikejian.customer.domain.BaseEntity;
import com.yikejian.customer.domain.account.Account;
import com.yikejian.customer.domain.title.Title;
import org.apache.commons.lang.StringUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * <code>Customer</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
@Entity
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue
    private Long customerId;
    /**
     * 客户名
     */
    private String customerName;
    /**
     * 性别
     */
    private Gender gender;
    /**
     * 微信open customerId
     */
    private String openId;
    /**
     * 移动电话号码
     */
    private String mobileNumber;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 账户
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;
    /**
     * 头衔
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "title_id")
    private Title title;
    /**
     * 客户反馈
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Opinion> opinionSet;

    public Customer() {
    }

    public Customer(boolean isNew) {
        if (isNew) {
            this.customerId = null;
            this.title = new Title(1L);
            this.account = new Account(0D, 0D, 0);
            this.setDeleted(0);
            this.setEffective(1);
        }
    }

    public Customer(String openId) {
        this(true);
        this.openId = openId;
    }

    public Customer asNew() {
        this.customerId = null;
        this.title = new Title(1L);
        this.account = new Account(0D, 0D, 0);
        this.setDeleted(0);
        this.setEffective(1);
        return this;
    }

    public Customer mergeOther(Customer other) {
        if (StringUtils.isNotBlank(other.getCustomerName())) {
            setCustomerName(other.getCustomerName());
        }
        if (other.getGender() != null) {
            setGender(other.getGender());
        }
        if (StringUtils.isNotBlank(other.getOpenId())) {
            setOpenId(other.getOpenId());
        }
        if (StringUtils.isNotBlank(other.getMobileNumber())) {
            setMobileNumber(other.getMobileNumber());
        }
        if (StringUtils.isNotBlank(other.getBirthday())) {
            setBirthday(other.getBirthday());
        }
        if (other.getAccount() != null) {
            setAccount(other.getAccount());
        }
        if (other.getOpinionSet() != null) {
            setOpinionSet(other.getOpinionSet());
        }
        if (other.getEffective() != null) {
            setEffective(other.getEffective());
        }
        if (other.getDeleted() != null) {
            setDeleted(other.getDeleted());
        }
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Set<Opinion> getOpinionSet() {
        return opinionSet;
    }

    public void setOpinionSet(Set<Opinion> opinionSet) {
        this.opinionSet = opinionSet;
    }
}