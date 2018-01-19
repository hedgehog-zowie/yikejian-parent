package com.yikejian.customer.repository;

import com.yikejian.customer.domain.title.Title;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>UserRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/12/19 16:50
 */
@Repository
public interface TitleRepository extends PagingAndSortingRepository<Title, Long>, JpaSpecificationExecutor<Title> {

    Title findByTitleId(Long titleId);

}
