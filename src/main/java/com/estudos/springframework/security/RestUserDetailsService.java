package com.estudos.springframework.security;

import com.estudos.springframework.repository.RestUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RestUserDetailsService implements UserDetailsService{
    private final RestUserRepository restUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(restUserRepository.findByUsername(username))
                .map(RestUserDetails::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Not found user with username " + username)
                );
    }
}
