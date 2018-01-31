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

    private InventoryExceptionCodeConstants inventoryExceptionCodeConstants;

    public InventoryServiceException(String message) {
        super(message);
        this.inventoryExceptionCodeConstants = InventoryExceptionCodeConstants.OTHER_ERROR;
    }

    public InventoryServiceException(InventoryExceptionCodeConstants inventoryExceptionCodeConstants,
                                     String message) {
        super(message);
        this.inventoryExceptionCodeConstants = inventoryExceptionCodeConstants;
    }

    public InventoryExceptionCodeConstants getInventoryExceptionCodeConstants() {
        return inventoryExceptionCodeConstants;
    }

    public void setInventoryExceptionCodeConstants(InventoryExceptionCodeConstants inventoryExceptionCodeConstants) {
        this.inventoryExceptionCodeConstants = inventoryExceptionCodeConstants;
    }

}
