package com.yikejian.user.domain.role;

import com.yikejian.user.api.v1.dto.RoleDto;
import com.yikejian.user.domain.BaseEntity;
import com.yikejian.user.domain.user.User;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
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

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> userSet;

    public Role() {
    }

    public Role(String roleName, String authorities) {
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

    public void fromRoleDto(RoleDto roleDto) {
        if (StringUtils.isNotBlank(roleDto.getRoleName())) {
            setRoleName(roleDto.getRoleName());
        }
        if (StringUtils.isNotBlank(roleDto.getAuthorities())) {
            setAuthorities(roleDto.getAuthorities());
        }
        if (roleDto.getEffective() != null) {
            setEffective(roleDto.getEffective());
        }
        if (roleDto.getDeleted() != null) {
            setDeleted(roleDto.getDeleted());
        }
    }

    public RoleDto toRoleDto() {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(getRoleId());
        roleDto.setRoleName(getRoleName());
        roleDto.setAuthorities(getAuthorities());
        roleDto.setEffective(getEffective());
        roleDto.setLastModifiedBy(getLastModifiedBy());
        roleDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return roleDto;
    }

}
