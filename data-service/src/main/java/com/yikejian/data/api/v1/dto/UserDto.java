package com.yikejian.data.api.v1.dto;

import java.util.Date;

/**
 * @author jackalope
 * @Title: RequestUser
 * @Package com.yikejian.user.request
 * @Description: TODO
 * @date 2018/1/14 17:59
 */
public class UserDto {
    private Long userId;
    private String userName;
    private Long roleId;
    private String roleName;
    private Integer effective;
    private Integer deleted;
    private String lastModifiedBy;
    private Date lastModifiedAt;

    public UserDto() {
    }

    public UserDto(String userName, Long roleId) {
        this.userName = userName;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
