package com.yikejian.customer.domain.customer;

import com.yikejian.customer.api.v1.dto.CustomerDto;
import com.yikejian.customer.domain.BaseEntity;
import com.yikejian.customer.domain.title.Title;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;

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
     * open id
     */
    private String openId;
    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 移动电话号码
     */
    private String mobileNumber;
    /**
     * 密码（MD5）
     */
    private String password;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 账户余额
     */
    private Double account;
    /**
     * 积分
     */
    private Integer point;
    /**
     * 头衔
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "title_id")
    private Title title;

    public void fromCustomerDto(CustomerDto customerDto) {
        if (StringUtils.isNotBlank(customerDto.getCustomerName())) {
            setCustomerName(customerDto.getCustomerName());
        }
        if (customerDto.getStartTime() != null) {
            setDeleted(customerDto.getDeleted());
        }
        if (customerDto.getEndTime() != null) {
            setDeleted(customerDto.getDeleted());
        }
        if (customerDto.getEffective() != null) {
            setEffective(customerDto.getEffective());
        }
        if (customerDto.getDeleted() != null) {
            setDeleted(customerDto.getDeleted());
        }
    }

    public CustomerDto toCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(getCustomerId());
        customerDto.setCustomerName(getCustomerName());
        customerDto.setEffective(getEffective());
        customerDto.setLastModifiedBy(getLastModifiedBy());
        customerDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return customerDto;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}