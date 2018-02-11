package com.yikejian.payment.exception;

import org.omg.CORBA.UNKNOWN;

/**
 * <code>InventoryExceptionCode</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/31 15:16
 */
public enum PaymentExceptionCode {

    /**
     * 签名失败
     */
    SIGNATURE_ERROR,
    /**
     * 其他错误
     */
    OTHER_ERROR,
    /**
     * 未知的支付方式
     */
    UNKNOWN_PAY_TYPE,
    /**
     * 余额不足
     */
    INSUFFICIENT_BALANCE,
    /**
     * 未知的客户
     */
    UNKNOWN_CUSTOMER,
    /**
     * 未知账户
     */
    UNKNOWN_ACCOUT,
    /**
     * 未知订单
     */
    UNKNOWN_ORDER,
    /**
     * 金额不符
     */
    MONEY_DIFFER,

}
