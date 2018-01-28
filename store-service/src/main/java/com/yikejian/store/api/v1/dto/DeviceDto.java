package com.yikejian.store.api.v1.dto;

import java.util.List;

/**
 * @author jackalope
 * @Title: DeviceDto
 * @Package com.yikejian.store.api.v1.dto
 * @Description: TODO
 * @date 2018/1/28 9:38
 */
public class DeviceDto {

    private Long deviceId;
    private String deviceCode;
    private String deviceName;
    private String roomName;
    private List<Long> products;

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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<Long> getProducts() {
        return products;
    }

    public void setProducts(List<Long> products) {
        this.products = products;
    }
}
