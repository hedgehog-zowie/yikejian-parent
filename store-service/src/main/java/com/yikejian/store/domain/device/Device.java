package com.yikejian.store.domain.device;

import com.google.common.collect.Sets;
import com.yikejian.store.api.v1.dto.DeviceDto;
import com.yikejian.store.api.v1.dto.DeviceProductDto;
import com.yikejian.store.api.v1.dto.ProductDto;
import com.yikejian.store.domain.BaseEntity;
import com.yikejian.store.domain.store.Store;
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

    public void fromDeviceDto(DeviceDto deviceDto) {
        if (StringUtils.isNotBlank(deviceDto.getDeviceCode())) {
            setDeviceCode(deviceDto.getDeviceCode());
        }
        if (StringUtils.isNotBlank(deviceDto.getDeviceName())) {
            setDeviceName(deviceDto.getDeviceName());
        }
        if (StringUtils.isNotBlank(deviceDto.getRoomName())) {
            setRoomName(deviceDto.getRoomName());
        }
        if (StringUtils.isNotBlank(deviceDto.getInformation())) {
            setInformation(deviceDto.getInformation());
        }
        // // TODO: 2018/1/18 move to service ( and store)
//        if (deviceDto.getProductList() != null && deviceDto.getProductList().size() > 0) {
//            deviceProductSet = Sets.newHashSet();
//            for(DeviceProductDto deviceProductDto: deviceDto.getProductList()){
//                DeviceProduct deviceProduct = new DeviceProduct();
//                deviceProduct.fromProductDto(deviceProductDto);
//                deviceProduct.setDevice(this);
//                deviceProductSet.add(deviceProduct);
//            }
//            setDeviceProductSet(deviceProductSet);
//        }
        if (deviceDto.getEffective() != null) {
            setEffective(deviceDto.getEffective());
        }
        if (deviceDto.getDeleted() != null) {
            setDeleted(deviceDto.getDeleted());
        }
    }

    public DeviceDto toDeviceDto() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setDeviceId(getDeviceId());
        deviceDto.setDeviceCode(getDeviceCode());
        deviceDto.setDeviceName(getDeviceName());
        deviceDto.setRoomName(getRoomName());
        deviceDto.setInformation(getInformation());
        // TODO: 2018/1/18 move to service
//        for(DeviceProduct deviceProduct: deviceProductSet){
//            
//        }
        // TODO: 2018/1/18 move to service
        deviceDto.setStoreId(store.getStoreId());
        deviceDto.setStoreName(store.getStoreName());
        deviceDto.setEffective(getEffective());
        deviceDto.setDeleted(getDeleted());
        deviceDto.setLastModifiedBy(getLastModifiedBy());
        deviceDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return deviceDto;
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
