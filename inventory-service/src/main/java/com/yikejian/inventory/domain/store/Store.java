package com.yikejian.inventory.domain.store;

import java.util.Set;

/**
 * <code>Store</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
public class Store {

    private Long storeId;
    /**
     * 开始营业时间(小时，如10表示10点整)
     */
    private Integer startTime;
    /**
     * 结束营业时间(小时，如23表示23点整)
     */
    private Integer endTime;
    /**
     * 预约规则：单个时间片的长度，单位分钟
     */
    private Integer unitDuration;
    /**
     * 预约规则：单个时间片内允许的最大预约次数（库存数量）
     */
    private Integer unitTimes;
    /**
     * 产品
     */
    private Set<StoreProduct> storeProductSet;
    /**
     * 设备
     */
    private Set<Device> deviceSet;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
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

    public Set<StoreProduct> getStoreProductSet() {
        return storeProductSet;
    }

    public void setStoreProductSet(Set<StoreProduct> storeProductSet) {
        this.storeProductSet = storeProductSet;
    }

    public Set<Device> getDeviceSet() {
        return deviceSet;
    }

    public void setDeviceSet(Set<Device> deviceSet) {
        this.deviceSet = deviceSet;
    }
}