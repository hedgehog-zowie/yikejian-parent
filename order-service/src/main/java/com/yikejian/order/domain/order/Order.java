package com.yikejian.order.domain.order;

import com.yikejian.order.api.vi.dto.OrderDto;
import com.yikejian.order.domain.BaseEntity;
import org.apache.commons.lang.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

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
    private String orderName;
    private Integer startTime;
    private Integer endTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
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

    public void fromOrderDto(OrderDto orderDto) {
        if (StringUtils.isNotBlank(orderDto.getOrderName())) {
            setOrderName(orderDto.getOrderName());
        }
        if (orderDto.getStartTime() != null) {
            setDeleted(orderDto.getDeleted());
        }
        if (orderDto.getEndTime() != null) {
            setDeleted(orderDto.getDeleted());
        }
        if (orderDto.getEffective() != null) {
            setEffective(orderDto.getEffective());
        }
        if (orderDto.getDeleted() != null) {
            setDeleted(orderDto.getDeleted());
        }
    }

    public OrderDto toOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(getOrderId());
        orderDto.setOrderName(getOrderName());
        orderDto.setStartTime(getStartTime());
        orderDto.setEndTime(getEndTime());
        orderDto.setEffective(getEffective());
        orderDto.setLastModifiedBy(getLastModifiedBy());
        orderDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return orderDto;
    }

}
