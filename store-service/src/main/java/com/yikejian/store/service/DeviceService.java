package com.yikejian.store.service;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.store.domain.store.Device;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.repository.DeviceRepository;
import com.yikejian.store.repository.DeviceRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @HystrixCommand
    public Device saveDevice(Device device) {
        return deviceRepository.save(transDevice(device));
    }

    @HystrixCommand
    public List<Device> saveDevices(List<Device> deviceList) {
        List<Device> newDeviceList = Lists.newArrayList();
        for (Device device : deviceList) {
            newDeviceList.add(transDevice(device));
        }
        return (List<Device>) deviceRepository.save(newDeviceList);
    }

    private Device transDevice(Device device) {
        Device newDevice = device;
        if (device.getDeviceId() != null) {
            Device oldDevice = deviceRepository.findByDeviceId(device.getDeviceId());
            newDevice = oldDevice.mergeOther(device);
        }
        return newDevice;
    }

    @HystrixCommand
    public Device getDeviceById(Long storeId) {
        return deviceRepository.findByDeviceId(storeId);
    }

    @HystrixCommand
    public List<Device> getDeviceByStoreId(Long storeId) {
        Store store = new Store(storeId);
        Device device = new Device();
        device.setStore(store);
        device.setDeleted(0);
        device.setEffective(1);
        return deviceRepository.findAll(storeSpec(device));
    }

    private Specification<Device> storeSpec(final Device device) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (device != null) {
                if (device.getStore() != null && device.getStore().getStoreId() != null) {
                    Join<Device, Store> join = root.join("store");
                    predicateList.add(cb.equal(join.<Long>get("storeId"), device.getStore().getStoreId()));
                }
                if (StringUtils.isNotBlank(device.getDeviceCode())) {
                    predicateList.add(cb.like(root.get("deviceCode").as(String.class), "%" + device.getDeviceCode() + "%"));
                }
                if (StringUtils.isNotBlank(device.getDeviceName())) {
                    predicateList.add(cb.like(root.get("deviceName").as(String.class), "%" + device.getDeviceName() + "%"));
                }
                if (StringUtils.isNotBlank(device.getRoomName())) {
                    predicateList.add(cb.like(root.get("roomName").as(String.class), "%" + device.getRoomName() + "%"));
                }
                if (StringUtils.isNotBlank(device.getInformation())) {
                    predicateList.add(cb.like(root.get("information").as(String.class), "%" + device.getInformation() + "%"));
                }
                if (device.getEffective() != null) {
                    predicateList.add(cb.equal(root.get("effective").as(Integer.class), device.getEffective()));
                }
                if (device.getDeleted() != null) {
                    predicateList.add(cb.equal(root.get("deleted").as(Integer.class), device.getDeleted()));
                }
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return cb.and(predicateList.toArray(predicates));
        };
    }
    
}
