package com.yikejian.product.domain.product;

import com.yikejian.product.api.v1.dto.ProductDto;
import com.yikejian.product.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

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
     * 开始时间(小时，如10表示10点整)
     */
    private Integer startTime;
    /**
     * 结束时间(小时，如23表示23点整)
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
}