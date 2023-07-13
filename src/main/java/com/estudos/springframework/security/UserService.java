package com.estudos.springframework.security;

import com.estudos.springframework.dto.UserCredentialsRequest;
import com.estudos.springframework.dto.UserRequest;
import com.estudos.springframework.dto.UserResponse;
import com.estudos.springframework.exceptions.EmailAlreadyRegisteredException;
import com.estudos.springframework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;
    @Transactional
    public UserResponse registryUser(UserRequest userRequest) throws EmailAlreadyRegisteredException{
        final String email = userRequest.getEmail();
        if(userRepository.findByEmail(email).isPresent()){
            throw new EmailAlreadyRegisteredException(email);
        }
        User user = User.builder()
                .email(email)
                .password(encoder.encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .build();
        userRepository.save(user);
        return UserResponse.builder()
                .email(user.getEmail())
                .token(jwtService.generateToken(email))
                .build();
    }

    @Transactional(readOnly = true)
    public UserResponse login(UserCredentialsRequest credentials) throws AuthenticationException {
        var authenticationToken = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
        var authentication = authenticationManager.authenticate(authenticationToken);
        return UserResponse.builder()
            .email(authentication.getName())
            .token(jwtService.generateToken(authentication.getName()))
            .build();
    }
}
