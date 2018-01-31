package com.yikejian.inventory.domain.inventory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author jackalope
 * @Title: InventoryEvent
 * @Package com.yikejian.inventory.domain.inventory
 * @Description: TODO
 * @date 2018/1/19 21:05
 */
@Entity
public class InventoryEvent {

    @Id
    @GeneratedValue
    private Long inventoryEventId;
    /**
     * 店铺ID
     */
    private Long storeId;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 预约时间
     */
    private String pieceTime;
    /**
     * 事件类型
     */
    private InventoryEventType inventoryEventType;

    public InventoryEvent() {
    }

    public InventoryEvent(Long storeId, Long productId, String pieceTime) {
        this.storeId = storeId;
        this.productId = productId;
        this.pieceTime = pieceTime;
    }

    public InventoryEvent(Long storeId, Long productId, String pieceTime, InventoryEventType inventoryEventType) {
        this.storeId = storeId;
        this.productId = productId;
        this.pieceTime = pieceTime;
        this.inventoryEventType = inventoryEventType;
    }

    public Long getInventoryEventId() {
        return inventoryEventId;
    }

    public void setInventoryEventId(Long inventoryEventId) {
        this.inventoryEventId = inventoryEventId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getPieceTime() {
        return pieceTime;
    }

    public void setPieceTime(String pieceTime) {
        this.pieceTime = pieceTime;
    }

    public InventoryEventType getInventoryEventType() {
        return inventoryEventType;
    }

    public void setInventoryEventType(InventoryEventType inventoryEventType) {
        this.inventoryEventType = inventoryEventType;
    }
}
