package com.yikejian.store.domain.store;

import com.yikejian.store.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Objects;

/**
 * <code>DeviceProduct</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/17 17:11
 */
@Entity
public class DeviceProduct extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 设备
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private Device device;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceProduct that = (DeviceProduct) o;
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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
