package com.yikejian.user.domain.user;

import com.google.common.collect.Lists;
import com.yikejian.user.domain.role.Authority;
import com.yikejian.user.domain.role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * <code>MyUserDetails</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/12/19 17:13
 */
public class MyUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(User user) {
        this.username = user.getName();
        this.password = user.getPassword();
        this.authorities = translate(user.getRole());
    }

    private Collection<? extends GrantedAuthority> translate(Role role) {
        return Lists.newArrayList(
                Authority.transToArray(role.getAuthorities()).stream().
                        map(authority -> new SimpleGrantedAuthority(authority.toUpperCase())).
                        collect(Collectors.toList()));
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
