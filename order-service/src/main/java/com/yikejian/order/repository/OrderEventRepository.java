package com.yikejian.order.repository;

import com.yikejian.order.domain.order.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>OrderRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface OrderEventRepository extends PagingAndSortingRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Order findByOrderId(Long orderId);

}
