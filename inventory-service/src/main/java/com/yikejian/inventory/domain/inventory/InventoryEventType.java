package com.yikejian.inventory.domain.inventory;

/**
 * @author jackalope
 * @Title: InventoryEventType
 * @Package com.yikejian.inventory.domain.inventory
 * @Description: TODO
 * @date 2018/1/19 21:05
 */
public enum InventoryEventType {

    /**
     * 递增已预定库存
     */
    INCREASE_STOCK,
    /**
     * 递减已预定库存
     */
    DECREASE_STOCK,
    /**
     * 递减资源
     */
    DECREASE_RESOURCE,
    /**
     * 递增资源
     */
    INCREASE_RESOURCE,

}
