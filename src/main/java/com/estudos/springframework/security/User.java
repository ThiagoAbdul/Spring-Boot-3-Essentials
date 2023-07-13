package com.estudos.springframework.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Column(length = 50, nullable = false, unique = true)
    @NotBlank(message = "E-mail cannot be null or blank")
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "Password cannot be null or blank")
    private String password;
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        switch (role){
            case ADMIN:
                authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getRole()));
            case USER:
                authorities.add(new SimpleGrantedAuthority(Role.USER.getRole()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
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
