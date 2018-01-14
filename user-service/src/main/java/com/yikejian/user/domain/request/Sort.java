package com.yikejian.user.domain.request;

import org.springframework.data.domain.Sort.Direction;

/**
 * @author jackalope
 * @Title: Sort
 * @Package com.yikejian.user.domain.request
 * @Description: TODO
 * @date 2018/1/14 18:22
 */
public class Sort {

    private String field;
    private Direction direction;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
