package com.yikejian.user.secure;

import com.yikejian.user.entity.Role;
import com.yikejian.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <code>UserDetails</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/12/19 17:13
 */
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;

    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = translate(user.getRoles());
    }

    private Collection<? extends GrantedAuthority> translate(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            String name = role.getName().toUpperCase();
            if (!name.startsWith("ROLE_")) {
                name = "ROLE_" + name;
            }
            authorities.add(new SimpleGrantedAuthority(name));
        }
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
