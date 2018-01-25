package com.yikejian.inventory.domain.store;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

/**
 * <code>StoreProduct</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/17 17:06
 */
public class StoreProduct {

    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 开始营业时间(精确到分，如1020表示10点20分)
     */
    private String startTime;
    /**
     * 结束营业时间(精确到分，如1020表示10点20分)
     */
    private String endTime;
    /**
     * 删除标识
     */
    private Integer deleted = 0;
    /**
     * 有效标识
     */
    private Integer effective = 1;
    /**
     * 店铺
     */
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getEffective() {
        return effective;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
