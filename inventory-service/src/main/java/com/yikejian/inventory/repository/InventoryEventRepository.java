package com.yikejian.inventory.repository;

import com.yikejian.inventory.domain.inventory.Inventory;
import com.yikejian.inventory.domain.inventory.InventoryEvent;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

/**
 * <code>InventoryRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface InventoryEventRepository extends PagingAndSortingRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {

    Stream<InventoryEvent> findByInventoryId(Long inventoryId);

}
