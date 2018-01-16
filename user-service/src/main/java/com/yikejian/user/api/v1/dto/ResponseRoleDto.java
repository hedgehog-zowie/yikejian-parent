package com.yikejian.user.api.v1.dto;

import java.util.List;

/**
 * <code>ResponseRoleDto</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/15 11:28
 */
public class ResponseRoleDto {

    private List<RoleDto> roleList;
    private Pagination pagination;

    public ResponseRoleDto() {
    }

    public ResponseRoleDto(List<RoleDto> roleList) {
        this.roleList = roleList;
    }

    public ResponseRoleDto(List<RoleDto> roleList, Pagination pagination) {
        this.roleList = roleList;
        this.pagination = pagination;
    }

    public List<RoleDto> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleDto> roleList) {
        this.roleList = roleList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
