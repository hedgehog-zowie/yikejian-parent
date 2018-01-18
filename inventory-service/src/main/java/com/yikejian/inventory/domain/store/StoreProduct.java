package com.yikejian.inventory.domain.store;

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
