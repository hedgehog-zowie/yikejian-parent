package com.yikejian.inventory.repository;

import com.yikejian.inventory.domain.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>InventoryRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {

    Inventory findByInventoryId(Long inventoryId);

}
