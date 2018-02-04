package com.yikejian.store.repository;

import com.yikejian.store.domain.banner.Banner;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>DeviceRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/18 15:17
 */
@Repository
public interface BannerRepository extends PagingAndSortingRepository<Banner, Long>, JpaSpecificationExecutor<Banner> {

}
