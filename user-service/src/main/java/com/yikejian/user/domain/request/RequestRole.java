package com.yikejian.user.domain.request;

/**
 * @author jackalope
 * @Title: RequestUser
 * @Package com.yikejian.user.domain.request
 * @Description: TODO
 * @date 2018/1/14 17:59
 */
public class RequestRole {
    private String roleName;
    private String authority;
    private Integer effective;
    private Integer deleted;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
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
