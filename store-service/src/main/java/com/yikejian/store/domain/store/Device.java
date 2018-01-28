package com.yikejian.store.domain.store;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yikejian.store.api.v1.dto.DeviceDto;
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
import java.util.List;
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
//    @JsonManagedReference
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store store;
    /**
     * 设备支持的产品
     */
//    @JsonBackReference
    @JsonManagedReference
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    private Set<DeviceProduct> deviceProductSet;

    public Device() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device that = (Device) o;
        return Objects.equals(deviceCode, that.deviceCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceCode);
    }

    public Device mergeOther(Device other) {
        if (StringUtils.isNotBlank(other.getDeviceCode())) {
            setDeviceCode(other.getDeviceCode());
        }
        if (StringUtils.isNotBlank(other.getDeviceName())) {
            setDeviceName(other.getDeviceName());
        }
        if (StringUtils.isNotBlank(other.getRoomName())) {
            setRoomName(other.getRoomName());
        }
        if (StringUtils.isNotBlank(other.getInformation())) {
            setInformation(other.getInformation());
        }
        if (other.getEffective() != null) {
            setEffective(other.getEffective());
        }
        if (other.getDeleted() != null) {
            setDeleted(other.getDeleted());
        }
        return this;
    }

    public DeviceDto toDeviceDto(){
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setDeviceId(getDeviceId());
        deviceDto.setDeviceCode(getDeviceCode());
        deviceDto.setDeviceName(getDeviceName());
        deviceDto.setRoomName(getRoomName());
        List<Long> productIdList = Lists.newArrayList();
        for(DeviceProduct deviceProduct: getDeviceProductSet()){
            productIdList.add(deviceProduct.getProductId());
        }
        deviceDto.setProducts(productIdList);
        return deviceDto;
    }

    public Device fromDeviceDto(DeviceDto deviceDto){
        if (StringUtils.isNotBlank(deviceDto.getDeviceCode())) {
            setDeviceCode(deviceDto.getDeviceCode());
        }
        if (StringUtils.isNotBlank(deviceDto.getDeviceName())) {
            setDeviceName(deviceDto.getDeviceName());
        }
        if (StringUtils.isNotBlank(deviceDto.getRoomName())) {
            setRoomName(deviceDto.getRoomName());
        }
        Set<DeviceProduct> deviceProductSet = Sets.newHashSet();
        for(Long productId: deviceDto.getProducts()){
            DeviceProduct deviceProduct = new DeviceProduct();
            deviceProduct.setProductId(productId);
            deviceProduct.setDevice(this);
            deviceProduct.setEffective(1);
            deviceProduct.setDeleted(0);
            deviceProductSet.add(deviceProduct);
        }
        setDeviceProductSet(deviceProductSet);
        return this;
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
