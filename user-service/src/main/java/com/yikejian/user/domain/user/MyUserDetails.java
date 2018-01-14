package com.yikejian.user.domain.user;

import com.yikejian.user.domain.role.Authority;
import com.yikejian.user.domain.role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.authorities = translate(user.getRole());
    }

    private Collection<? extends GrantedAuthority> translate(Role role) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        Arrays.stream(Authority.transToArray(role.getAuthorities())).forEach(
                authority -> authorityList.add(new SimpleGrantedAuthority(authority.toUpperCase()))
        );

//        roles.stream().map(
//                role -> Arrays.stream(Authority.transToArray(role.getAuthorities())).map(
//                        authority -> authorityList.add(new SimpleGrantedAuthority(authority.toUpperCase()))));

//        for (Role role : roles) {
//            String authorities = role.getAuthorities();
//            for (String authority : Authority.transToArray(authorities)) {
//                authorityList.add(new SimpleGrantedAuthority(authority.toUpperCase()));
//            }
//        }
        return authorityList;
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
