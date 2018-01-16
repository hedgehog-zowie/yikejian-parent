package com.yikejian.data.repository;

import com.yikejian.data.domain.data.Data;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>DataRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface DataRepository extends PagingAndSortingRepository<Data, Long>, JpaSpecificationExecutor<Data> {

    Data findByDataId(Long dataId);

}
