package com.asgdrones.drones.security;

import com.asgdrones.drones.domain.Login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserPrincipal implements UserDetails {
    private Login user;

    public MyUserPrincipal(Login login){
        System.out.println(login);
        this.user = login;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAccess()));
        return AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAccess());
    }

    @Override
    public String getPassword() {
        System.out.println(user.getPassword());
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        System.out.println(user.getUsername());
        return user.getUsername();
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
