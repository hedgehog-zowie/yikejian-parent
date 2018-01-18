package com.yikejian.store.domain.store;

import com.yikejian.store.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author jackalope
 * @Title: Device
 * @Package com.yikejian.store.domain.device
 * @Description: TODO
 * @date 2018/1/17 0:03
 */
@Entity
public class Device extends BaseEntity {

    @Id
    @GeneratedValue
    private Long deviceId;
    /**
     * 设备编号
     */
    private String deviceCode;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 所在房间
     */
    private String roomName;
    /**
     * 设备信息
     */
    private String information;
    /**
     * 设备所在店铺
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store store;
    /**
     * 设备支持的产品
     */
    @OneToMany(mappedBy = "device", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<DeviceProduct> deviceProductSet;

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

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Set<DeviceProduct> getDeviceProductSet() {
        return deviceProductSet;
    }

    public void setDeviceProductSet(Set<DeviceProduct> deviceProductSet) {
        this.deviceProductSet = deviceProductSet;
    }

}
