package com.yikejian.payment.domain.payment;

import com.yikejian.payment.api.vi.dto.PaymentDto;
import com.yikejian.payment.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * <code>Payment</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue
    private Long paymentId;
    private String paymentName;
    private Integer startTime;
    private Integer endTime;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
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

    public void fromPaymentDto(PaymentDto paymentDto) {
        if (StringUtils.isNotBlank(paymentDto.getPaymentName())) {
            setPaymentName(paymentDto.getPaymentName());
        }
        if (paymentDto.getStartTime() != null) {
            setDeleted(paymentDto.getDeleted());
        }
        if (paymentDto.getEndTime() != null) {
            setDeleted(paymentDto.getDeleted());
        }
        if (paymentDto.getEffective() != null) {
            setEffective(paymentDto.getEffective());
        }
        if (paymentDto.getDeleted() != null) {
            setDeleted(paymentDto.getDeleted());
        }
    }

    public PaymentDto toPaymentDto() {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPaymentId(getPaymentId());
        paymentDto.setPaymentName(getPaymentName());
        paymentDto.setStartTime(getStartTime());
        paymentDto.setEndTime(getEndTime());
        paymentDto.setEffective(getEffective());
        paymentDto.setLastModifiedBy(getLastModifiedBy());
        paymentDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return paymentDto;
    }

}
