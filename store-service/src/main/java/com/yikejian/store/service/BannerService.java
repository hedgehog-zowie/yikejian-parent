package com.yikejian.store.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.store.domain.banner.Banner;
import com.yikejian.store.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jackalope
 * @Title: StoreProductService
 * @Package com.yikejian.store.service
 * @Description: TODO
 * @date 2018/1/18 23:23
 */
@Service
public class BannerService {

    private BannerRepository bannerRepository;

    @Autowired
    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @HystrixCommand
    public List<Banner> getAllBanner() {
        return (List<Banner>) bannerRepository.findAll();
    }

}
