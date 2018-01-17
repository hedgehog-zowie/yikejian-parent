package com.yikejian.customer.domain.customer;

import com.yikejian.customer.api.v1.dto.CustomerDto;
import com.yikejian.customer.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private String customerName;
    private Integer startTime;
    private Integer endTime;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

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
        customerDto.setStartTime(getStartTime());
        customerDto.setEndTime(getEndTime());
        customerDto.setEffective(getEffective());
        customerDto.setLastModifiedBy(getLastModifiedBy());
        customerDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return customerDto;
    }

}
