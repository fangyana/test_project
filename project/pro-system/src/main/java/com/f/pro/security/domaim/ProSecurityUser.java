package com.f.pro.security.domaim;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description: 后台登录成功用户基础信息
 */
@Setter
@Getter
@Accessors(chain = true)
public class ProSecurityUser implements UserDetails {

    private LoginType loginType;
    private Integer userId;
    private String username;
    private String password;
    private Integer deptId;
    private Collection<? extends GrantedAuthority> authorities;

    public ProSecurityUser(Integer userId, String username, String password, Integer deptId, Collection<? extends GrantedAuthority> authorities, LoginType loginType) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.loginType = loginType;
        this.deptId = deptId;
    }

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
}
