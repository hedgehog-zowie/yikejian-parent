package com.yikejian.data.domain.data;

import com.yikejian.data.api.v1.dto.DataDto;
import com.yikejian.data.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * <code>Data</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
public class Data extends BaseEntity {

    @Id
    @GeneratedValue
    private Long dataId;
    private String dataName;
    private Integer startTime;
    private Integer endTime;

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
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

    public void fromDataDto(DataDto dataDto) {
        if (StringUtils.isNotBlank(dataDto.getDataName())) {
            setDataName(dataDto.getDataName());
        }
        if (dataDto.getStartTime() != null) {
            setDeleted(dataDto.getDeleted());
        }
        if (dataDto.getEndTime() != null) {
            setDeleted(dataDto.getDeleted());
        }
        if (dataDto.getEffective() != null) {
            setEffective(dataDto.getEffective());
        }
        if (dataDto.getDeleted() != null) {
            setDeleted(dataDto.getDeleted());
        }
    }

    public DataDto toDataDto() {
        DataDto dataDto = new DataDto();
        dataDto.setDataId(getDataId());
        dataDto.setDataName(getDataName());
        dataDto.setStartTime(getStartTime());
        dataDto.setEndTime(getEndTime());
        dataDto.setEffective(getEffective());
        dataDto.setLastModifiedBy(getLastModifiedBy());
        dataDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return dataDto;
    }

}
