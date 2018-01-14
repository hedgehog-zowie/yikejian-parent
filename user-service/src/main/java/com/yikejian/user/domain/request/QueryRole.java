package com.yikejian.user.domain.request;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.domain.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class QueryRole {

    private RequestRole role;
    private Pagination pagination;
    private Sort sort;

    public RequestRole getRole() {
        return role;
    }

    public void setRole(RequestRole role) {
        this.role = role;
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
