package com.backend.recuirement.service;

import com.backend.recuirement.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String username;

    private final String password;

    private final String status;

    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user){
        this.username= user.getEmail();
        this.password= user.getPasswordHashed();
        this.status = user.getStatus();

        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserType().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return "ACTIVE".equalsIgnoreCase(status);
    }
}
