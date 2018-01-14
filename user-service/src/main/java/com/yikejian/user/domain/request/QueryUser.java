package com.yikejian.user.domain.request;

/**
 * @author jackalope
 * @Title: QueryUser
 * @Package com.yikejian.user.domain.request
 * @Description: TODO
 * @date 2018/1/14 21:50
 */
public class QueryUser {

    private RequestUser user;
    private Pagination pagination;
    private Sort sort;

    public RequestUser getUser() {
        return user;
    }

    public void setUser(RequestUser user) {
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
