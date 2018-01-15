package com.yikejian.user.api.v1.dto;

import java.util.Date;

/**
 * @author jackalope
 * @Title: RequestUser
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 17:59
 */
public class RoleDto {
    private Long roleId;
    private String roleName;
    private String authorities;
    private Integer effective;
    private Integer deleted;
    private String lastModifiedBy;
    private Date lastModifiedAt;

    public RoleDto() {
    }

    public RoleDto(String roleName, String authorities) {
        this.roleName = roleName;
        this.authorities = authorities;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getEffective() {
        return effective;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
