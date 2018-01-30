package com.yikejian.inventory.domain.store;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;
import java.util.Set;

/**
 * @author jackalope
 * @Title: Device
 * @Package com.yikejian.store.domain.device
 * @Description: TODO
 * @date 2018/1/17 0:03
 */
public class Device {

    private Long deviceId;
    /**
     * 设备支持的产品
     */
    @JsonManagedReference
    private Set<DeviceProduct> deviceProductSet;
    /**
     * 店铺
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store store;

    public Device() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(deviceId, device.deviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId);
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Set<DeviceProduct> getDeviceProductSet() {
        return deviceProductSet;
    }

    public void setDeviceProductSet(Set<DeviceProduct> deviceProductSet) {
        this.deviceProductSet = deviceProductSet;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
