package com.yikejian.order.domain.order;

import com.yikejian.order.api.vi.dto.OrderDto;
import com.yikejian.order.domain.BaseEntity;
import com.yikejian.order.domain.item.Item;
import org.apache.commons.lang.StringUtils;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

/**
 * <code>Order</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:55
 */
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    private Long orderId;
    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 店铺ID
     */
    private Long storeId;
    /**
     * 订单金额
     */
    private Double amount;
    /**
     * 实付金额
     */
    private Double actualAmount;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<Item> itemSet;
    /**
     * 附加信息
     */
    private OrderExtra orderExtra;

    public void fromOrderDto(OrderDto orderDto) {
    }

    public OrderDto toOrderDto() {
        return null;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Set<Item> getItemSet() {
        return itemSet;
    }

    public void setItemSet(Set<Item> itemSet) {
        this.itemSet = itemSet;
    }
}
