package com.yikejian.payment.api.v1.dto;

/**
 * @author jackalope
 * @Title: Sort
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 18:22
 */
public class Sort {

    private String field;
    private String direction;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
