package com.yikejian.product.domain.product;

import com.yikejian.product.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <code>Product</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue
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
     * 开始营业时间(精确到分，如1020表示10点20分)
     */
    private String startTime;
    /**
     * 结束营业时间(精确到分，如1020表示10点20分)
     */
    private String endTime;
    /**
     * 介绍
     */
    private String introduction;
    /**
     * LOGO
     */
    private byte[] logo;

    public Product mergeOther(Product other) {
        if (StringUtils.isNotBlank(other.getProductName())) {
            setProductName(other.getProductName());
        }
        if (other.getPrice() != null) {
            setPrice(other.getPrice());
        }
        if (other.getDuration() != null) {
            setDuration(other.getDuration());
        }
        if (StringUtils.isNotBlank(other.getStartTime())) {
            setStartTime(other.getStartTime());
        }
        if (StringUtils.isNotBlank(other.getEndTime())) {
            setEndTime(other.getEndTime());
        }
        if (StringUtils.isNotBlank(other.getIntroduction())) {
            setIntroduction(other.getIntroduction());
        }
        if (other.getLogo() != null) {
            setLogo(other.getLogo());
        }
        if (other.getEffective() != null) {
            setEffective(other.getEffective());
        }
        if (other.getDeleted() != null) {
            setDeleted(other.getDeleted());
        }
        return this;
    }

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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
}