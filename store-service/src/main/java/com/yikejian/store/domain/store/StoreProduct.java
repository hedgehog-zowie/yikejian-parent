package com.yikejian.store.domain.store;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yikejian.store.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * <code>StoreProduct</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/17 17:06
 */
@Entity
public class StoreProduct extends BaseEntity {

    @Id
    @GeneratedValue
    private Long storeProductId;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 开始营业时间(精确到分，如1020表示10点20分)
     */
    private String startTime;
    /**
     * 结束营业时间(精确到分，如1020表示10点20分)
     */
    private String endTime;
    /**
     * 店铺
     */
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store store;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreProduct that = (StoreProduct) o;

        if (!productId.equals(that.productId)) return false;
        return store.equals(that.store);
    }

    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + store.hashCode();
        return result;
    }

    public StoreProduct mergeOther(StoreProduct other) {
        if (other.getEffective() != null) {
            setEffective(other.getEffective());
        }
        if (other.getDeleted() != null) {
            setDeleted(other.getDeleted());
        }
        return this;
    }

    public Long getStoreProductId() {
        return storeProductId;
    }

    public void setStoreProductId(Long storeProductId) {
        this.storeProductId = storeProductId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
