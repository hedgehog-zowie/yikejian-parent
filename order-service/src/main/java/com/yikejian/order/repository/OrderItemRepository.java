package com.yikejian.order.repository;

import com.yikejian.order.domain.order.Order;
import com.yikejian.order.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <code>OrderRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {

    List<OrderItem> findByOrder(Order order);

    void deleteByOrder(Order order);

}
