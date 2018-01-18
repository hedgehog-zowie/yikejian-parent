package com.yikejian.store.domain.store;

import com.yikejian.store.api.v1.dto.StoreDto;
import com.yikejian.store.domain.BaseEntity;
import com.yikejian.store.domain.device.Device;
import org.apache.commons.lang.StringUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
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

//    public void fromStoreDto(StoreDto storeDto) {
//        if (StringUtils.isNotBlank(storeDto.getStoreName())) {
//            setStoreName(storeDto.getStoreName());
//        }
//        if (StringUtils.isNotBlank(storeDto.getAddress())) {
//            setAddress(storeDto.getAddress());
//        }
//        if (StringUtils.isNotBlank(storeDto.getPhoneNumber())) {
//            setPhoneNumber(storeDto.getPhoneNumber());
//        }
//        if (storeDto.getStartTime() != null) {
//            setStartTime(storeDto.getDeleted());
//        }
//        if (storeDto.getEndTime() != null) {
//            setEndTime(storeDto.getDeleted());
//        }
//        if (StringUtils.isNotBlank(storeDto.getTraffic())) {
//            setTraffic(storeDto.getTraffic());
//        }
//        if (storeDto.getLongitude() != null) {
//            setLongitude(storeDto.getLongitude());
//        }
//        if (storeDto.getLatitude() != null) {
//            setLatitude(storeDto.getLatitude());
//        }
//        if (storeDto.getEffective() != null) {
//            setEffective(storeDto.getEffective());
//        }
//        if (storeDto.getDeleted() != null) {
//            setDeleted(storeDto.getDeleted());
//        }
//    }
//
//    public StoreDto toStoreDto() {
//        StoreDto storeDto = new StoreDto();
//        storeDto.setStoreId(getStoreId());
//        storeDto.setStoreName(getStoreName());
//        storeDto.setAddress(getAddress());
//        storeDto.setPhoneNumber(getPhoneNumber());
//        storeDto.setStartTime(getStartTime());
//        storeDto.setEndTime(getEndTime());
//        storeDto.setTraffic(getTraffic());
//        storeDto.setLongitude(getLongitude());
//        storeDto.setLatitude(getLatitude());
//        storeDto.setEffective(getEffective());
//        storeDto.setDeleted(getDeleted());
//        storeDto.setLastModifiedBy(getLastModifiedBy());
//        storeDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
//        return storeDto;
//    }

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