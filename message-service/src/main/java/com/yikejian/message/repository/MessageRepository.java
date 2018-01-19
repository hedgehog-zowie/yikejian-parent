package com.yikejian.message.repository;

import com.yikejian.message.domain.message.Message;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>ProductRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long>, JpaSpecificationExecutor<Message> {

}
