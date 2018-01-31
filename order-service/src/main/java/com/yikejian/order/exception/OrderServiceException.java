package com.yikejian.order.exception;

/**
 * <code>OrderServiceException</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 14:19
 */
public class OrderServiceException extends RuntimeException {

    public OrderServiceException(Throwable cause) {
        super(cause);
    }

    public OrderServiceException(String msg) {
        super(msg);
    }

}
