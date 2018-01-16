package com.yikejian.user.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yikejian.user.api.v1.dto.UserDto;
import com.yikejian.user.domain.BaseEntity;
import com.yikejian.user.domain.role.Role;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Objects;

/**
 * <code>User</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/12/19 16:50
 */
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private Long userId;
    private String userName;
    @JsonIgnore
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
    }

    public User(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    public void fromUserDto(UserDto userDto) {
        if (StringUtils.isNotBlank(userDto.getUserName())) {
            setUserName(userDto.getUserName());
        }
        if (userDto.getEffective() != null) {
            setEffective(userDto.getEffective());
        }
        if (userDto.getDeleted() != null) {
            setDeleted(userDto.getDeleted());
        }
    }

    public UserDto toUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUserId(getUserId());
        userDto.setUserName(getUserName());
        userDto.setRoleId(getRole().getRoleId());
        userDto.setRoleName(getRole().getRoleName());
        userDto.setEffective(getEffective());
        userDto.setDeleted(getDeleted());
        userDto.setLastModifiedBy(getLastModifiedBy());
        userDto.setLastModifiedAt(getLastModifiedAt() == null ? null : new Date(getLastModifiedAt()));
        return userDto;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
