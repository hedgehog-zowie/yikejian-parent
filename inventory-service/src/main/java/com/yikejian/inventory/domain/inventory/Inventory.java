package com.yikejian.inventory.domain.inventory;

import com.yikejian.inventory.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <code>Inventory</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
@Entity
public class Inventory extends BaseEntity{

    @Id
    @GeneratedValue
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
     * 可预约次数
     */
    private Integer stock;
    /**
     * 日期
     */
    private String day;

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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
