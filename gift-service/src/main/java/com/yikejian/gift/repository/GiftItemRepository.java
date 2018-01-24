package com.yikejian.gift.repository;

import com.yikejian.gift.domain.item.GiftItem;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>CouponRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface GiftItemRepository extends PagingAndSortingRepository<GiftItem, Long>, JpaSpecificationExecutor<GiftItem> {

    GiftItem findByGiftItemId(Long giftItemId);

    GiftItem findByEffectiveAndDeleted(Integer effective, Integer deleted);

}
