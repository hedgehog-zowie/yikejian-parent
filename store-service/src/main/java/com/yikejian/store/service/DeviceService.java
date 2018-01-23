package com.yikejian.store.service;

import com.yikejian.store.repository.DeviceProductRepository;
import com.yikejian.store.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

/**
 * <code>DeviceService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/18 15:17
 */
@Deprecated
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

    // TODO: 2018/1/18 Dont't need?

}
