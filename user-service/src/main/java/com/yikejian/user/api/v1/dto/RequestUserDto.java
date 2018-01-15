package com.yikejian.user.api.v1.dto;

/**
 * @author jackalope
 * @Title: QueryUser
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:50
 */
public class RequestUserDto {

    private UserDto user;
    private Pagination pagination;
    private Sort sort;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
