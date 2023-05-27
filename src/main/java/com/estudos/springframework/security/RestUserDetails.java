package com.estudos.springframework.security;

import com.estudos.springframework.domain.RestUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


@Getter
public class RestUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<GrantedAuthority> authorities;
    private final String DEFAULT_SEPARATOR = " ";

    public RestUserDetails(RestUser restUser){
        this.username = restUser.getUsername();
        this.password = restUser.getPassword();
        this.authorities = getAuthoritiesAsCollection(restUser.getAuthorities());
    }

    private Collection<GrantedAuthority> getAuthoritiesAsCollection(String authorities){
        return Arrays.stream(authorities.split(DEFAULT_SEPARATOR))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
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
