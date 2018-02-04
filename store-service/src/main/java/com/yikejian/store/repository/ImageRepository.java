package com.yikejian.store.repository;

import com.yikejian.store.domain.store.Image;
import com.yikejian.store.domain.store.Store;
import com.yikejian.store.domain.store.StoreProduct;
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
public interface ImageRepository extends PagingAndSortingRepository<Image, Long>, JpaSpecificationExecutor<Image> {

    void deleteByStore(Store store);

}
