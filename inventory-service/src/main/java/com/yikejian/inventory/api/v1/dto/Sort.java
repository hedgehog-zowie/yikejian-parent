package com.yikejian.inventory.api.v1.dto;

/**
 * @author jackalope
 * @Title: Sort
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 18:22
 */
public class Sort {

    private String field;
    private String order;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
