package com.yikejian.user.api.v1.dto;

import com.yikejian.user.domain.role.Role;

/**
 * @author jackalope
 * @Title: QueryRole
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 21:51
 */
public class RequestRole {

    private Role role;
    private Pagination pagination;
    private Sort sorter;

    public RequestRole() {
    }

    public RequestRole(Role role, Pagination pagination, Sort sorter) {
        this.role = role;
        this.pagination = pagination;
        this.sorter = sorter;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
