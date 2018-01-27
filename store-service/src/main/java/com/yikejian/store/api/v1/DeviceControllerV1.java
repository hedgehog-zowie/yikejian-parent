package com.yikejian.store.api.v1;

import com.yikejian.store.domain.store.Device;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.exception.StoreServiceException;
import com.yikejian.store.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * <code>StoreControllerV1</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:57
 */
@RestController
@RequestMapping("/v1")
public class DeviceControllerV1 {

    private DeviceService deviceService;

    @Autowired
    public DeviceControllerV1(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/store/{store_id}/device")
    public ResponseEntity addDevice(
            final @PathVariable(value = "store_id") Long storeId,
            final @RequestBody Device device) {
        // todo send log
        device.setDeviceId(null);
        device.setStore(new Store(storeId));
        return Optional.ofNullable(deviceService.saveDevice(device))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not save device."));
    }

    @PutMapping("/store/{store_id}/device")
    public ResponseEntity updateStore(final @PathVariable(value = "store_id") Long storeId,
                                      final @RequestBody Device device) {
        // todo send log
        device.setStore(new Store(storeId));
        return Optional.ofNullable(deviceService.saveDevice(device))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not save device."));
    }

    @RequestMapping(value = "/store/{store_id}/devices", method = RequestMethod.GET)
    public ResponseEntity getDevices(final @PathVariable(value = "store_id") Long storeId) {
        // todo send log
        return Optional.ofNullable(deviceService.getDeviceByStoreId(storeId))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new StoreServiceException("Not found store."));
    }

}
