package com.yikejian.inventory.exception;

/**
 * <code>InventoryServiceException</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 14:19
 */
public class InventoryServiceException extends RuntimeException {

    private InventoryExceptionCode inventoryExceptionCode;

    public InventoryServiceException(Throwable cause, InventoryExceptionCode inventoryExceptionCode) {
        super(cause);
        this.inventoryExceptionCode = inventoryExceptionCode;
    }

    public InventoryServiceException(String message) {
        super(message);
        this.inventoryExceptionCode = InventoryExceptionCode.OTHER_ERROR;
    }

    public InventoryServiceException(InventoryExceptionCode inventoryExceptionCode,
                                     String message) {
        super(message);
        this.inventoryExceptionCode = inventoryExceptionCode;
    }

    public InventoryExceptionCode getInventoryExceptionCode() {
        return inventoryExceptionCode;
    }

    public void setInventoryExceptionCode(InventoryExceptionCode inventoryExceptionCode) {
        this.inventoryExceptionCode = inventoryExceptionCode;
    }

}
