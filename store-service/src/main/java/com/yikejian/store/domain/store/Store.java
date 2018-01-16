package com.yikejian.store.domain.store;

import com.yikejian.store.api.vi.dto.StoreDto;
import com.yikejian.store.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * <code>Store</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
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
     * 开始营业时间
     */
    private Integer startTime;
    /**
     * 结束营业时间
     */
    private Integer endTime;
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

    public void fromStoreDto(StoreDto storeDto) {
        if (StringUtils.isNotBlank(storeDto.getStoreName())) {
            setStoreName(storeDto.getStoreName());
        }
        if (storeDto.getStartTime() != null) {
            setDeleted(storeDto.getDeleted());
        }
        if (storeDto.getEndTime() != null) {
            setDeleted(storeDto.getDeleted());
        }
        if (storeDto.getEffective() != null) {
            setEffective(storeDto.getEffective());
        }
        if (storeDto.getDeleted() != null) {
            setDeleted(storeDto.getDeleted());
        }
    }

    public StoreDto toStoreDto() {
        StoreDto storeDto = new StoreDto();
        storeDto.setStoreId(getStoreId());
        storeDto.setStoreName(getStoreName());
        storeDto.setStartTime(getStartTime());
        storeDto.setEndTime(getEndTime());
        storeDto.setEffective(getEffective());
        storeDto.setLastModifiedBy(getLastModifiedBy());
        storeDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return storeDto;
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
}
