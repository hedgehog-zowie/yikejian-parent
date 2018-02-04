package com.yikejian.user.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yikejian.user.domain.BaseEntity;
import com.yikejian.user.domain.role.Role;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
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
    private Long id;
    private String name;
    private String avatar;
//    @JsonIgnore
    private String password;
    //    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
    }

    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User(Long id, String name, String password, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    public User mergeOther(User other) {
        if (StringUtils.isNotBlank(other.getName())) {
            setName(other.getName());
        }
        if (StringUtils.isNotBlank(other.getAvatar())) {
            setAvatar(other.getAvatar());
        }
        if (StringUtils.isNotBlank(other.getPassword())) {
            setPassword(other.getPassword());
        }
        if (other.getRole() != null) {
            setRole(other.getRole());
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
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
