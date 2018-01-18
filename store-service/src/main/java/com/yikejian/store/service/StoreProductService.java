package com.yikejian.store.service;

import com.yikejian.store.repository.StoreProductRepository;
import com.yikejian.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

/**
 * @author jackalope
 * @Title: StoreProductService
 * @Package com.yikejian.store.service
 * @Description: TODO
 * @date 2018/1/18 23:23
 */
@Deprecated
@Service
public class StoreProductService {

    private StoreRepository storeRepository;
    private StoreProductRepository storeProductRepository;
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public StoreProductService(StoreRepository storeRepository,
                               StoreProductRepository storeProductRepository,
                               OAuth2RestTemplate oAuth2RestTemplate) {
        this.storeRepository = storeRepository;
        this.storeProductRepository = storeProductRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    // TODO: 2018/1/18 Dont't need?

}
