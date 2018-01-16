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
    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 礼品卡ID
     */
    private Long giftId;
    /**
     * 合计（金额）
     */
    private Double amount;
    /**
     * 支付方式
     */
    private PaymentType paymentType;

    public void fromPaymentDto(PaymentDto paymentDto) {
    }

    public PaymentDto toPaymentDto() {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPaymentId(getPaymentId());
        return paymentDto;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
