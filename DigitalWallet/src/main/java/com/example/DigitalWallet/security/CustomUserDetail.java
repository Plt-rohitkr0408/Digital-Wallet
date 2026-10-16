package com.example.DigitalWallet.security;

import com.example.DigitalWallet.UserAuthority.Authority;
import com.example.DigitalWallet.model.Authorities;
import com.example.DigitalWallet.model.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetail implements UserDetails {

    public final User user;


    public CustomUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> allAuthorities = new ArrayList<>();
        allAuthorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().toString()));
        Set<Authorities> authoritySet = Authority.getAuthority(user.getRole());
        authoritySet.forEach(auth-> allAuthorities.add(new SimpleGrantedAuthority(auth.toString())));
        return allAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
