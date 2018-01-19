package com.yikejian.customer.domain.title;

import com.yikejian.customer.domain.BaseEntity;
import com.yikejian.customer.domain.customer.Customer;

import javax.persistence.*;
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
    private Double condition;
    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY)
    private Set<Customer> customerSet;

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

    public Double getCondition() {
        return condition;
    }

    public void setCondition(Double condition) {
        this.condition = condition;
    }

    public Set<Customer> getCustomerSet() {
        return customerSet;
    }

    public void setCustomerSet(Set<Customer> customerSet) {
        this.customerSet = customerSet;
    }
}
