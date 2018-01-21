package com.yikejian.store.domain.store;

import com.yikejian.store.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Set;

/**
 * <code>Store</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue
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
     * 产品
     */
    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<StoreProduct> storeProductSet;
    /**
     * 设备
     */
    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Device> deviceSet;

    public Store mergeOtherStore(Store other) {
        if (StringUtils.isNotBlank(other.getStoreName())) {
            setStoreName(other.getStoreName());
        }
        if (StringUtils.isNotBlank(other.getAddress())) {
            setAddress(other.getAddress());
        }
        if (StringUtils.isNotBlank(other.getPhoneNumber())) {
            setPhoneNumber(other.getPhoneNumber());
        }
        if (other.getStartTime() != null) {
            setStartTime(other.getDeleted());
        }
        if (other.getEndTime() != null) {
            setEndTime(other.getDeleted());
        }
        if (StringUtils.isNotBlank(other.getTraffic())) {
            setTraffic(other.getTraffic());
        }
        if (other.getLongitude() != null) {
            setLongitude(other.getLongitude());
        }
        if (other.getLatitude() != null) {
            setLatitude(other.getLatitude());
        }
        if (other.getEffective() != null) {
            setEffective(other.getEffective());
        }
        if (other.getDeleted() != null) {
            setDeleted(other.getDeleted());
        }
        // check product set
        if (other.getStoreProductSet() != null && other.getStoreProductSet().size() > 0) {
            storeProductSet.addAll(other.getStoreProductSet());
        }
        // check device set
        if (other.getDeviceSet() != null && other.getDeviceSet().size() > 0) {
            deviceSet.addAll(other.getDeviceSet());
        }
        return this;
    }

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