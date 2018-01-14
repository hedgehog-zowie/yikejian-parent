package com.yikejian.user.domain.request;

/**
 * @author jackalope
 * @Title: RequestUser
 * @Package com.yikejian.user.domain.request
 * @Description: TODO
 * @date 2018/1/14 17:59
 */
public class RequestUser {
    private String userName;
    private Long roleId;
    private Integer effective;
    private Integer deleted;

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
}
