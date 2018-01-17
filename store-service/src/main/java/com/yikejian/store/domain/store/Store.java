package com.yikejian.store.domain.store;

import com.yikejian.store.api.vi.dto.StoreDto;
import com.yikejian.store.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * <code>Store</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
public class Store extends BaseEntity {

    @Id
    @GeneratedValue
    private Long storeId;
    private String storeName;
    private Integer startTime;
    private Integer endTime;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public void fromStoreDto(StoreDto storeDto) {
        if (StringUtils.isNotBlank(storeDto.getStoreName())) {
            setStoreName(storeDto.getStoreName());
        }
        if (storeDto.getStartTime() != null) {
            setDeleted(storeDto.getDeleted());
        }
        if (storeDto.getEndTime() != null) {
            setDeleted(storeDto.getDeleted());
        }
        if (storeDto.getEffective() != null) {
            setEffective(storeDto.getEffective());
        }
        if (storeDto.getDeleted() != null) {
            setDeleted(storeDto.getDeleted());
        }
    }

    public StoreDto toStoreDto() {
        StoreDto storeDto = new StoreDto();
        storeDto.setStoreId(getStoreId());
        storeDto.setStoreName(getStoreName());
        storeDto.setStartTime(getStartTime());
        storeDto.setEndTime(getEndTime());
        storeDto.setEffective(getEffective());
        storeDto.setLastModifiedBy(getLastModifiedBy());
        storeDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return storeDto;
    }

}
