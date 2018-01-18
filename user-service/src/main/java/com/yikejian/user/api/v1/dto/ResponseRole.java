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

    private List<Role> roleList;
    private Pagination pagination;

    public ResponseRole() {
    }

    public ResponseRole(List<Role> roleList) {
        this.roleList = roleList;
    }

    public ResponseRole(List<Role> roleList, Pagination pagination) {
        this.roleList = roleList;
        this.pagination = pagination;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
