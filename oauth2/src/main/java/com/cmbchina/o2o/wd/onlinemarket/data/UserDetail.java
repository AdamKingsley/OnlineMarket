package com.cmbchina.o2o.wd.onlinemarket.data;

import com.cmbchina.o2o.wd.onlinemarket.constant.UserType;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class UserDetail implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String id;
    private String username;
    private String password;
    private String salt;
    private String telephone;
    private String email;
    private String avatar;
    private List<Role> authorities = Lists.newArrayList();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    public void addRole(Role role) {
        authorities.add(role);
    }
}
