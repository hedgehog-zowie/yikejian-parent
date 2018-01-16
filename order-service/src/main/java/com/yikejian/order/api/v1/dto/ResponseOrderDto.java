package com.yikejian.order.api.vi.dto;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseOrderDto {

    private List<OrderDto> orderList;
    private Pagination pagination;

    public ResponseOrderDto(List<OrderDto> orderList) {
        this.orderList = orderList;
    }

    public ResponseOrderDto(List<OrderDto> orderList, Pagination pagination) {
        this.orderList = orderList;
        this.pagination = pagination;
    }

    public List<OrderDto> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDto> orderList) {
        this.orderList = orderList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
