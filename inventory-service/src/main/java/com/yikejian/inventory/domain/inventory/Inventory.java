package com.yikejian.inventory.domain.inventory;

import com.yikejian.inventory.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

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
public class Inventory extends BaseEntity {

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
        if (!stock.equals(inventory.stock)) return false;
        if (!day.equals(inventory.day)) return false;
        return pieceTime.equals(inventory.pieceTime);

    }

    @Override
    public int hashCode() {
        int result = storeId.hashCode();
        result = 31 * result + productId.hashCode();
        result = 31 * result + stock.hashCode();
        result = 31 * result + day.hashCode();
        result = 31 * result + pieceTime.hashCode();
        return result;
    }

    public Inventory mergeOtherInventory(Inventory other){
        if (other.getStoreId() != null) {
            setStoreId(other.getStoreId());
        }
        if (other.getProductId() != null) {
            setProductId(other.getProductId());
        }
        if (other.getStoreId() != null) {
            setStoreId(other.getStoreId());
        }
        if (StringUtils.isNotBlank(other.getDay())) {
            setDay(other.getDay());
        }
        return this;
    }

    public Inventory incorporate(InventoryEvent inventoryEvent) {
        switch (inventoryEvent.getInventoryEventType()) {
            case DECREASE_STOCK:
                stock--;
                break;
            case INCREASE_STOCK:
                stock++;
                break;
            default:
                break;
        }
        return this;
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
