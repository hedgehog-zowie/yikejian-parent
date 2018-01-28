package com.yikejian.store.repository;

import com.yikejian.store.domain.store.Device;
import com.yikejian.store.domain.store.Store;
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
public interface DeviceRepository extends PagingAndSortingRepository<Device, Long>, JpaSpecificationExecutor<Device> {

    Device findByDeviceId(Long deviceId);

//    List<Device> findByStoreIdAndDeleted(Long storeId, Integer deleted);

    void deleteByStore(Store store);

}
