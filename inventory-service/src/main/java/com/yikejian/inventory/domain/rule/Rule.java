package com.yikejian.inventory.domain.rule;

import com.yikejian.inventory.domain.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author jackalope
 * @Title: Rule
 * @Package com.yikejian.inventory.domain.rule
 * @Description: TODO
 * @date 2018/1/17 0:27
 */
public class Rule extends BaseEntity {

    @Id
    @GeneratedValue
    private Long ruleId;
    /**
     * 店铺ID
     */
    private Long storeId;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 单个时间片的长度，单位分钟
     */
    private Integer unitDuration;
    /**
     * 单个时间片内允许的最大预约次数（库存数量）
     */
    private Integer unitTimes;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getUnitDuration() {
        return unitDuration;
    }

    public void setUnitDuration(Integer unitDuration) {
        this.unitDuration = unitDuration;
    }

    public Integer getUnitTimes() {
        return unitTimes;
    }

    public void setUnitTimes(Integer unitTimes) {
        this.unitTimes = unitTimes;
    }
}
