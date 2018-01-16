package com.yikejian.store.domain.device;

import com.yikejian.store.domain.BaseEntity;
import com.yikejian.store.domain.store.Store;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author jackalope
 * @Title: Device
 * @Package com.yikejian.store.domain.device
 * @Description: TODO
 * @date 2018/1/17 0:03
 */
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
     * 所属店铺
     */
    private Store store;
    /**
     * 所在房间
     */
    private String roomName;
    /**
     * 设备信息
     */
    private String information;
    /**
     * 设备支持的产品ID，以英文逗号分隔
     */
    private String products;

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

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }
}
