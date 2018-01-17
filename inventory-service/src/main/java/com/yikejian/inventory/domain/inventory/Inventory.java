package com.yikejian.inventory.domain.inventory;

import com.yikejian.inventory.api.v1.dto.InventoryDto;
import com.yikejian.inventory.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * <code>Inventory</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
public class Inventory extends BaseEntity {

    @Id
    @GeneratedValue
    private Long inventoryId;
    private String inventoryName;
    private Integer startTime;
    private Integer endTime;

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
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

    public void fromInventoryDto(InventoryDto inventoryDto) {
        if (StringUtils.isNotBlank(inventoryDto.getInventoryName())) {
            setInventoryName(inventoryDto.getInventoryName());
        }
        if (inventoryDto.getStartTime() != null) {
            setDeleted(inventoryDto.getDeleted());
        }
        if (inventoryDto.getEndTime() != null) {
            setDeleted(inventoryDto.getDeleted());
        }
        if (inventoryDto.getEffective() != null) {
            setEffective(inventoryDto.getEffective());
        }
        if (inventoryDto.getDeleted() != null) {
            setDeleted(inventoryDto.getDeleted());
        }
    }

    public InventoryDto toInventoryDto() {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setInventoryId(getInventoryId());
        inventoryDto.setInventoryName(getInventoryName());
        inventoryDto.setStartTime(getStartTime());
        inventoryDto.setEndTime(getEndTime());
        inventoryDto.setEffective(getEffective());
        inventoryDto.setLastModifiedBy(getLastModifiedBy());
        inventoryDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return inventoryDto;
    }

}
