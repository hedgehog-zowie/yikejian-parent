package com.yikejian.customer.domain.title;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yikejian.customer.domain.BaseEntity;
import com.yikejian.customer.domain.customer.Customer;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author jackalope
 * @Title: Title
 * @Package com.yikejian.customer.domain.title
 * @Description: TODO
 * @date 2018/1/17 1:32
 */
@Entity
public class Title extends BaseEntity {

    @Id
    @GeneratedValue
    private Long titleId;
    /**
     * 等级名称
     */
    private String titleName;
    /**
     * 消费金额门槛
     */
    private Double threshold;
    /**
     * 会员
     */
    @JsonIgnore
    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY)
    private Set<Customer> customerSet;

    public Title() {
    }

    public Title(Long titleId) {
        this.titleId = titleId;
    }

    public Title mergeOther(Title other) {
        if (StringUtils.isNotBlank(other.getTitleName())) {
            setTitleName(other.getTitleName());
        }
        if (other.getThreshold() != null) {
            setThreshold(other.getThreshold());
        }
        if (other.getEffective() != null) {
            setEffective(other.getEffective());
        }
        if (other.getDeleted() != null) {
            setDeleted(other.getDeleted());
        }
        return this;
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public Set<Customer> getCustomerSet() {
        return customerSet;
    }

    public void setCustomerSet(Set<Customer> customerSet) {
        this.customerSet = customerSet;
    }
}
