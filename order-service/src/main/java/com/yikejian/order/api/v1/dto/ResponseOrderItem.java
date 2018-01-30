package com.yikejian.order.api.v1.dto;

import com.yikejian.order.domain.order.Order;
import com.yikejian.order.domain.order.OrderItem;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseOrderItem {

    private List<OrderItem> list;
    private Pagination pagination;

    public ResponseOrderItem(List<OrderItem> list) {
        this.list = list;
    }

    public ResponseOrderItem(List<OrderItem> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<OrderItem> getList() {
        return list;
    }

    public void setList(List<OrderItem> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
