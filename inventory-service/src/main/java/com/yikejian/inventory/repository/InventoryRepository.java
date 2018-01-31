package com.yikejian.inventory.repository;

import com.yikejian.inventory.domain.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

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

    Set<Inventory> findByStoreIdAndProductIdAndDay(Long storeId, Long productId, String day);

    void deleteByStoreIdAndProductIdAndDay(Long storeId, Long productId, String day);

    List<Inventory> findByStoreIdAndDay(Long storeId, String day);

    Long countByStoreIdAndProductIdAndDay(Long storeId, Long productId, String day);

    Inventory findByStoreIdAndProductIdAndDayAndPieceTime(Long storeId, Long productId, String day, String pieceTime);

    Inventory findByStoreIdAndProductIdAndPieceTime(Long storeId, Long productId, String pieceTime);

}
