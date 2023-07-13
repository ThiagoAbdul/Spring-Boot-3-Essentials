package com.estudos.springframework.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.estudos.springframework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private static final int TOKEN_BEGIN = 7;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if(isBearerToken(authorization)){
            String token = getAuthorizationToken(authorization);
            try{
                String email = jwtService.getSubjectFromToken(token);
                userRepository.findByEmail(email)
                        .ifPresent(user -> SecurityContextHolder.getContext()
                                .setAuthentication(new UsernamePasswordAuthenticationToken(
                                        user, null, user.getAuthorities())
                                )
                        );
            }
            catch (JWTVerificationException ignored){ }

        }

        filterChain.doFilter(request, response);
    }

    private boolean isBearerToken(String authorizationHeader){
        if(authorizationHeader == null) return false;
        return authorizationHeader.startsWith("Bearer");
    }

    private String getAuthorizationToken(String authorizationHeader){
        return authorizationHeader.substring(TOKEN_BEGIN);
    }
}
