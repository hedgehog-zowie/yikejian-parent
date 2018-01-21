package com.yikejian.user.api.v1.dto;

import com.yikejian.user.domain.role.Role;

import java.util.List;

/**
 * <code>ResponseRole</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseRole {

    private List<Role> list;
    private Pagination pagination;

    public ResponseRole() {
    }

    public ResponseRole(List<Role> list) {
        this.list = list;
    }

    public ResponseRole(List<Role> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<Role> getList() {
        return list;
    }

    public void setList(List<Role> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
