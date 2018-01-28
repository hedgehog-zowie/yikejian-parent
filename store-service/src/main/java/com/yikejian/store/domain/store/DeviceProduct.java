package com.yikejian.store.domain.store;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yikejian.store.domain.BaseEntity;

import javax.persistence.*;
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
    private Long deviceProductId;
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
     * 设备
     */
//    @JsonManagedReference
    @JsonBackReference
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

    public Long getDeviceProductId() {
        return deviceProductId;
    }

    public void setDeviceProductId(Long deviceProductId) {
        this.deviceProductId = deviceProductId;
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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
