package com.yikejian.store.repository;

import com.yikejian.store.domain.store.Store;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <code>StoreRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface StoreRepository extends PagingAndSortingRepository<Store, Long>, JpaSpecificationExecutor<Store> {

    Store findByStoreId(Long storeId);

    List<Store> findByEffectiveAndDeleted(Integer effective, Integer deleted);

}
