package com.yikejian.product.api.v1.dto;

import java.util.Date;

/**
 * <code>ProductDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 10:01
 */
public class ProductDto {

    private Long productId;
    /**
     * 名称
     */
    private String productName;
    /**
     * 价格
     */
    private Double price;
    /**
     * 时长
     */
    private Integer duration;
    /**
     * 开始时间
     */
    private Integer startTime;
    /**
     * 结束时间
     */
    private Integer endTime;
    /**
     * 介绍
     */
    private String introduction;
    /**
     * LOGO
     */
    private byte[] logo;
    /**
     * 有效标识
     */
    private Integer effective;
    /**
     * 删除标识
     */
    private Integer deleted;
    /**
     * 最近一次操作用户
     */
    private String lastModifiedBy;
    /**
     * 最近一次操作时间
     */
    private Date lastModifiedAt;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public Integer getEffective() {
        return effective;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
