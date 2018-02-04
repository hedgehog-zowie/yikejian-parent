package com.yikejian.store.exception;

/**
 * <code>StoreServiceException</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 14:19
 */
public class StoreServiceException extends RuntimeException {

    public StoreServiceException(Throwable cause) {
        super(cause);
    }

    public StoreServiceException(String msg) {
        super(msg);
    }

}
