package com.yikejian.user.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yikejian.user.domain.BaseEntity;
import com.yikejian.user.domain.role.Role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @ManyToOne
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
