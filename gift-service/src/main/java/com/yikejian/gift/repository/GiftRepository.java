package com.yikejian.gift.repository;

import com.yikejian.gift.domain.gift.Gift;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>GiftRepository</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Repository
public interface GiftRepository extends PagingAndSortingRepository<Gift, Long>, JpaSpecificationExecutor<Gift> {

    Gift findByGiftId(Long giftId);

    Gift findByEffectiveAndDeleted(Integer effective, Integer deleted);

}
