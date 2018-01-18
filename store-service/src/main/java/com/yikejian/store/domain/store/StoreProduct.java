package com.yikejian.store.domain.store;

import com.yikejian.store.domain.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

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
    private Long id;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 产品名称
     */
    @Transient
    private String productName;
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
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
