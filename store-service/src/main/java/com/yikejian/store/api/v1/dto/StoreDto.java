package com.yikejian.store.api.v1.dto;

import java.util.List;

/**
 * @author jackalope
 * @Title: StoreDto
 * @Package com.yikejian.store.api.v1.dto
 * @Description: TODO
 * @date 2018/1/28 9:37
 */
public class StoreDto {

    private Long storeId;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 店铺地址
     */
    private String address;
    /**
     * 电话
     */
    private String phoneNumber;
    /**
     * 开始营业时间(精确到分，如1020表示10点20分)
     */
    private String startTime;
    /**
     * 结束营业时间(精确到分，如1020表示10点20分)
     */
    private String endTime;
    /**
     * 预约规则：单个时间片的长度，单位分钟
     */
    private Integer unitDuration;
    /**
     * 预约规则：单个时间片内允许的最大预约次数（库存数量）
     */
    private Integer unitTimes;
    /**
     * 交通
     */
    private String traffic;
    /**
     * 经度
     */
    private Float longitude;
    /**
     * 纬度
     */
    private Float latitude;
    /**
     * 支持的产品
     */
    private List<Long> products;
    /**
     * 拥有的设备
     */
    private List<DeviceDto> devices;

    private Integer effective;
    private Integer deleted;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Integer getUnitDuration() {
        return unitDuration;
    }

    public void setUnitDuration(Integer unitDuration) {
        this.unitDuration = unitDuration;
    }

    public Integer getUnitTimes() {
        return unitTimes;
    }

    public void setUnitTimes(Integer unitTimes) {
        this.unitTimes = unitTimes;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public List<Long> getProducts() {
        return products;
    }

    public void setProducts(List<Long> products) {
        this.products = products;
    }

    public List<DeviceDto> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceDto> devices) {
        this.devices = devices;
    }

    public Integer getEffective() {
        return effective;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
