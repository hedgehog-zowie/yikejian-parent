package com.yikejian.inventory.domain.product;

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
public class Product {

    private Long productId;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

}