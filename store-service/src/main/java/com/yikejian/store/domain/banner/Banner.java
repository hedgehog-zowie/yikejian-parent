package com.yikejian.store.domain.banner;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yikejian.store.domain.BaseEntity;
import com.yikejian.store.domain.product.Product;
import com.yikejian.store.domain.store.Store;

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
public class Banner extends BaseEntity {

    @Id
    @GeneratedValue
    private Long bannerId;

    private String url;

    public Long getBannerId() {
        return bannerId;
    }

    public void setBannerId(Long bannerId) {
        this.bannerId = bannerId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}