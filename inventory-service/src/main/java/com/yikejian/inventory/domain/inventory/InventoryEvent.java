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
     * 库存ID
     */
    private Long inventoryId;
    /**
     * 事件类型
     */
    private InventoryEventType inventoryEventType;

    public Long getInventoryEventId() {
        return inventoryEventId;
    }

    public void setInventoryEventId(Long inventoryEventId) {
        this.inventoryEventId = inventoryEventId;
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
