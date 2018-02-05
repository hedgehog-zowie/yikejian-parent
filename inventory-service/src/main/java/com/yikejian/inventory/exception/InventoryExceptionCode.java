package com.yikejian.inventory.exception;

/**
 * <code>InventoryExceptionCode</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/31 15:16
 */
public enum InventoryExceptionCode {

    /**
     * 不存在的库存
     */
    NOT_EXISTS_INVENTORY,
    /**
     * 无足够库存
     */
    INSUFFICIENT_INVENTORY,
    /**
     * 店铺信息非法
     */
    ILLEGAL_STORE,
    /**
     * 产品信息非法
     */
    ILLEGAL_PRODUCT,
    /**
     * 订单信息非法
     */
    ILLEGAL_ORDER,
    /**
     * 日期非法
     */
    ILLEGAL_DAY,
    /**
     * 其他错误
     */
    OTHER_ERROR,;

}
