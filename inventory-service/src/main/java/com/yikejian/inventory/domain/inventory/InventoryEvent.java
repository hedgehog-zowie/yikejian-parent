package com.yikejian.inventory.domain.inventory;

/**
 * @author jackalope
 * @Title: InventoryEvent
 * @Package com.yikejian.inventory.domain.inventory
 * @Description: TODO
 * @date 2018/1/19 21:05
 */
public class InventoryEvent {

    private Long id;
    /**
     * 库存ID
     */
    private Long inventoryId;
    /**
     * 事件类型
     */
    private InventoryEventType inventoryEventType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public InventoryEventType getInventoryEventType() {
        return inventoryEventType;
    }

    public void setInventoryEventType(InventoryEventType inventoryEventType) {
        this.inventoryEventType = inventoryEventType;
    }
}
