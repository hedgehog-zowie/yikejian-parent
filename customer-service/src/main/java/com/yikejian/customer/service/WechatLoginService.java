package com.yikejian.customer.service;

import com.yikejian.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * <code>WeixinLoginService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/2/2 16:05
 */
public class WechatLoginService {

    private RestTemplate restTemplate;

    @Autowired
    public WechatLoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



}
