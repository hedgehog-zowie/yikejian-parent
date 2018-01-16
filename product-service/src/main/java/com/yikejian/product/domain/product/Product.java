package com.yikejian.product.domain.product;

import com.yikejian.product.api.v1.dto.ProductDto;
import com.yikejian.product.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

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

    public void fromProductDto(ProductDto productDto) {
        if (StringUtils.isNotBlank(productDto.getProductName())) {
            setProductName(productDto.getProductName());
        }
        if (productDto.getStartTime() != null) {
            setDeleted(productDto.getDeleted());
        }
        if (productDto.getEndTime() != null) {
            setDeleted(productDto.getDeleted());
        }
        if (productDto.getEffective() != null) {
            setEffective(productDto.getEffective());
        }
        if (productDto.getDeleted() != null) {
            setDeleted(productDto.getDeleted());
        }
    }

    public ProductDto toProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(getProductId());
        productDto.setProductName(getProductName());
        productDto.setStartTime(getStartTime());
        productDto.setEndTime(getEndTime());
        productDto.setEffective(getEffective());
        productDto.setLastModifiedBy(getLastModifiedBy());
        productDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return productDto;
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
