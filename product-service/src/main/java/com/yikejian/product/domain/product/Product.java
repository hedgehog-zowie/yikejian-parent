package com.yikejian.product.domain.product;

import com.yikejian.product.api.vi.dto.ProductDto;
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
    private String productName;
    private Integer startTime;
    private Integer endTime;

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

}
