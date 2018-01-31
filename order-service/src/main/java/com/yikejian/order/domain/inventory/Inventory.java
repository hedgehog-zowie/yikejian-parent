package com.yikejian.order.domain.inventory;

import javax.persistence.Entity;

/**
 * <code>Inventory</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
public class Inventory {

    private Long inventoryId;
    /**
     * 店铺ID
     */
    private Long storeId;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 初始预约次数，根据店铺、产品、设备计算的初始值
     */
    private Integer stock;
    /**
     * 已预约次数
     */
    private Integer bookedStock;
    /**
     * 剩余的可预约次数
     */
    private Integer restStock;
    /**
     * 日期
     */
    private String day;
    /**
     * 时间片(格式：yyyyMMddHHmm)
     */
    private String pieceTime;

    public Inventory() {
    }

    public Inventory(Long storeId, Long productId, Integer stock, String day, String pieceTime) {
        this.productId = productId;
        this.stock = stock;
        this.day = day;
        this.pieceTime = pieceTime;
        this.storeId = storeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inventory inventory = (Inventory) o;

        if (!storeId.equals(inventory.storeId)) return false;
        if (!productId.equals(inventory.productId)) return false;
        if (!day.equals(inventory.day)) return false;
        return pieceTime.equals(inventory.pieceTime);
    }

    @Override
    public int hashCode() {
        int result = storeId.hashCode();
        result = 31 * result + productId.hashCode();
        result = 31 * result + day.hashCode();
        result = 31 * result + pieceTime.hashCode();
        return result;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getBookedStock() {
        return bookedStock;
    }

    public void setBookedStock(Integer bookedStock) {
        this.bookedStock = bookedStock;
    }

    public Integer getRestStock() {
        return restStock;
    }

    public void setRestStock(Integer restStock) {
        this.restStock = restStock;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPieceTime() {
        return pieceTime;
    }

    public void setPieceTime(String pieceTime) {
        this.pieceTime = pieceTime;
    }
}
