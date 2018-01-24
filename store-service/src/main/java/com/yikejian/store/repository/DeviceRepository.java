package com.yikejian.store.repository;

import com.yikejian.store.domain.store.Device;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * <code>DeviceRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/18 15:17
 */
public interface DeviceRepository extends PagingAndSortingRepository<Device, Long>, JpaSpecificationExecutor<Device> {

    Device findByDeviceId(Long storeId);

    Device findByEffectiveAndDeleted(Integer effective, Integer deleted);

}
