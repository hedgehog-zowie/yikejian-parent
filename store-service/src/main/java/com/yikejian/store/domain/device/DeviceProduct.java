package com.yikejian.store.domain.device;

import com.yikejian.store.api.v1.dto.DeviceProductDto;
import com.yikejian.store.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * <code>DeviceProduct</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/17 17:11
 */
@Entity
public class DeviceProduct extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * 产品名称
     */
    private Long productId;
    /**
     * 设备
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private Device device;

    public void fromProductDto(DeviceProductDto deviceProductDto) {
        if (deviceProductDto.getId() != null) {
            setId(deviceProductDto.getId());
        }
        if (deviceProductDto.getProductId() != null) {
            setProductId(deviceProductDto.getProductId());
        }
    }

    public DeviceProductDto toDeviceProductDto(){
        DeviceProductDto deviceProductDto = new DeviceProductDto();
        deviceProductDto.setId(getId());
        deviceProductDto.setProductId(getProductId());
        deviceProductDto.setEffective(getEffective());
        deviceProductDto.setDeleted(getDeleted());
        deviceProductDto.setLastModifiedBy(getLastModifiedBy());
        deviceProductDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return deviceProductDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceProduct that = (DeviceProduct) o;

        if (!id.equals(that.id)) return false;
        return productId.equals(that.productId);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + productId.hashCode();
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
