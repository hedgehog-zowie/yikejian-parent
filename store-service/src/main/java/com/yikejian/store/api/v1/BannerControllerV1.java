package com.yikejian.store.api.v1;

import com.yikejian.store.api.v1.dto.RequestStore;
import com.yikejian.store.api.v1.dto.RequestStoreOfClient;
import com.yikejian.store.exception.StoreServiceException;
import com.yikejian.store.service.BannerService;
import com.yikejian.store.service.StoreService;
import com.yikejian.store.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

/**
 * @author jackalope
 * @Title: BannerControllerV1
 * @Package com.yikejian.store.api.v1
 * @Description: TODO
 * @date 2018/2/4 23:09
 */
@RestController
@RequestMapping("/v1")
public class BannerControllerV1 {

    private BannerService bannerService;

    @Autowired
    public BannerControllerV1(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping("/banners")
    public ResponseEntity getBanners() {
        // todo send log
            return Optional.ofNullable(bannerService.getAllBanner())
                    .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                    .orElseThrow(() -> new StoreServiceException("Not found any banner."));
    }

}
