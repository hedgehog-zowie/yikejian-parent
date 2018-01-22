package com.yikejian.user.api.v1.dto;

import com.yikejian.user.domain.user.User;

/**
 * @author jackalope
 * @Title: QueryUser
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:50
 */
public class RequestUser {

    private User user;
    private Pagination pagination;
    private Sort sorter;

    public RequestUser() {
    }

    public RequestUser(User user, Pagination pagination, Sort sorter) {
        this.user = user;
        this.pagination = pagination;
        this.sorter = sorter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Sort getSorter() {
        return sorter;
    }

    public void setSorter(Sort sorter) {
        this.sorter = sorter;
    }
}
