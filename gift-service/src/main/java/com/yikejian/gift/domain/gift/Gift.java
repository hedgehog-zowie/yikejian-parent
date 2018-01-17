package com.yikejian.gift.domain.gift;

import com.yikejian.gift.api.v1.dto.GiftDto;
import com.yikejian.gift.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * <code>Gift</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
public class Gift extends BaseEntity {

    @Id
    @GeneratedValue
    private Long giftId;
    /**
     * 礼物名称
     */
    private String giftName;
    /**
     * 价格
     */
    private Double price;
    /**
     * LOGO
     */
    private byte[] logo;
    /**
     * 开始时间戳
     */
    private Long startTime;
    /**
     * 结束时间戳
     */
    private Long endTime;

    public void fromGiftDto(GiftDto giftDto) {
        if (StringUtils.isNotBlank(giftDto.getGiftName())) {
            setGiftName(giftDto.getGiftName());
        }
        if (giftDto.getStartTime() != null) {
            setDeleted(giftDto.getDeleted());
        }
        if (giftDto.getEndTime() != null) {
            setDeleted(giftDto.getDeleted());
        }
        if (giftDto.getEffective() != null) {
            setEffective(giftDto.getEffective());
        }
        if (giftDto.getDeleted() != null) {
            setDeleted(giftDto.getDeleted());
        }
    }

    public GiftDto toGiftDto() {
        GiftDto giftDto = new GiftDto();
        giftDto.setGiftId(getGiftId());
        giftDto.setGiftName(getGiftName());
        giftDto.setEffective(getEffective());
        giftDto.setLastModifiedBy(getLastModifiedBy());
        giftDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return giftDto;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
