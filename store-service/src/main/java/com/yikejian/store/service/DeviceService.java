package com.yikejian.store.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yikejian.store.api.v1.dto.DeviceDto;
import com.yikejian.store.api.v1.dto.DeviceProductDto;
import com.yikejian.store.api.v1.dto.ProductDto;
import com.yikejian.store.domain.device.Device;
import com.yikejian.store.domain.device.DeviceProduct;
import com.yikejian.store.exception.StoreServiceException;
import com.yikejian.store.repository.DeviceProductRepository;
import com.yikejian.store.repository.DeviceRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * <code>DeviceService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/18 15:17
 */
@Service
public class DeviceService {

    private DeviceRepository deviceRepository;
    private DeviceProductRepository deviceProductRepository;
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository,
                         DeviceProductRepository deviceProductRepository,
                         OAuth2RestTemplate oAuth2RestTemplate) {
        this.deviceRepository = deviceRepository;
        this.deviceProductRepository = deviceProductRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    private DeviceDto deviceToDeviceDto(Device device) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setDeviceId(device.getDeviceId());
        deviceDto.setDeviceCode(device.getDeviceCode());
        deviceDto.setDeviceName(device.getDeviceName());
        deviceDto.setRoomName(device.getRoomName());
        deviceDto.setInformation(device.getInformation());
        deviceDto.setEffective(device.getEffective());
        deviceDto.setDeleted(device.getDeleted());
        deviceDto.setLastModifiedBy(device.getLastModifiedBy());
        deviceDto.setLastModifiedAt(device.getLastModifiedAt() == null ? null : new Date(device.getLastModifiedAt()));
        deviceDto.setStoreId(device.getStore().getStoreId());
        deviceDto.setStoreName(device.getStore().getStoreName());
        deviceDto.setProductList(Lists.newArrayList(
                device.getDeviceProductSet().stream().
                        map(this::deviceProductToDeviceProductDto).collect(Collectors.toList())
        ));
        return deviceDto;
    }

    private Device deviceDtoToDevice(DeviceDto deviceDto) {
        Device device;
        if (deviceDto.getDeviceId() != null) {
            device = deviceRepository.findByDeviceId(deviceDto.getDeviceId());
            if (device == null) {
                throw new StoreServiceException("未知的设备");
            }
        } else {
            device = new Device();
        }
        if (StringUtils.isNotBlank(deviceDto.getDeviceCode())) {
            device.setDeviceCode(deviceDto.getDeviceCode());
        }
        if (StringUtils.isNotBlank(deviceDto.getDeviceName())) {
            device.setDeviceName(deviceDto.getDeviceName());
        }
        if (StringUtils.isNotBlank(deviceDto.getRoomName())) {
            device.setRoomName(deviceDto.getRoomName());
        }
        if (StringUtils.isNotBlank(deviceDto.getInformation())) {
            device.setInformation(deviceDto.getInformation());
        }
        if (deviceDto.getEffective() != null) {
            device.setEffective(deviceDto.getEffective());
        }
        if (deviceDto.getDeleted() != null) {
            device.setDeleted(deviceDto.getDeleted());
        }
        // set device product
        if (deviceDto.getProductList() != null && deviceDto.getProductList().size() > 0) {
            device.setDeviceProductSet(Sets.newHashSet(
                    deviceDto.getProductList().stream().
                            map(this::deviceProductDtoToDeviceProduct).collect(Collectors.toList())
            ));
        }
        return device;
    }

    private DeviceProductDto deviceProductToDeviceProductDto(DeviceProduct deviceProduct) {
        DeviceProductDto deviceProductDto = new DeviceProductDto();
        deviceProductDto.setId(deviceProduct.getId());
        deviceProductDto.setProductId(deviceProduct.getProductId());
        deviceProductDto.setEffective(deviceProduct.getEffective());
        deviceProductDto.setDeleted(deviceProduct.getDeleted());
        deviceProductDto.setLastModifiedBy(deviceProduct.getLastModifiedBy());
        deviceProductDto.setLastModifiedAt(deviceProduct.getLastModifiedAt() == null ? null : new Date(deviceProduct.getLastModifiedAt()));
        // TODO: 2018/1/18 fetch product from product service
        ProductDto productDto = oAuth2RestTemplate.getForObject("get product url", ProductDto.class);
        deviceProductDto.setProductName(productDto.getProductName());
        return deviceProductDto;
    }

    private DeviceProduct deviceProductDtoToDeviceProduct(DeviceProductDto deviceProductDto) {
        DeviceProduct deviceProduct;
        if (deviceProductDto.getDeviceId() != null) {
            deviceProduct = deviceProductRepository.findOne(deviceProductDto.getId());
            if (deviceProduct == null) {
                throw new StoreServiceException("未找到产品: " + deviceProductDto.getId());
            }
        } else {
            deviceProduct = new DeviceProduct();
        }
        if (deviceProductDto.getProductId() == null) {
            throw new StoreServiceException("未设置产品ID");
        }
        deviceProduct.setProductId(deviceProductDto.getProductId());
        return deviceProduct;
    }

}
