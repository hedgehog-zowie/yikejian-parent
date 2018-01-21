package com.yikejian.user.domain.role;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yikejian.user.domain.BaseEntity;
import com.yikejian.user.domain.user.User;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

/**
 * <code>Role</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/12/19 16:51
 */
@Entity
public class Role extends BaseEntity {
    @Id
    @GeneratedValue
    private Long roleId;

    private String roleName;

    private String authorities;

//    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> userSet;

    public Role() {
    }

    public Role(Long roleId) {
        this.roleId = roleId;
    }

    public Role(String roleName, String authorities) {
        this.roleName = roleName;
        this.authorities = authorities;
    }

    public Role(Long roleId, String roleName, String authorities) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(roleId, role.roleId);
    }

    public Role mergeOtherRole(Role other) {
        if (StringUtils.isNotBlank(other.getRoleName())) {
            setRoleName(other.getRoleName());
        }
        if (StringUtils.isNotBlank(other.getAuthorities())) {
            setAuthorities(other.getAuthorities());
        }
        if (other.getEffective() != null) {
            setEffective(other.getEffective());
        }
        if (other.getDeleted() != null) {
            setDeleted(other.getDeleted());
        }
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
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

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

}
