package com.estudos.springframework.controller;

import com.estudos.springframework.dto.UserCredentialsRequest;
import com.estudos.springframework.dto.UserRequest;
import com.estudos.springframework.dto.UserResponse;
import com.estudos.springframework.exceptions.EmailAlreadyRegisteredException;
import com.estudos.springframework.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/registry")
    ResponseEntity<?> registry(@Valid @RequestBody UserRequest newUser) throws EmailAlreadyRegisteredException{
        UserResponse userResponse = userService.registryUser(newUser);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponse);


    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> login(@Valid @RequestBody UserCredentialsRequest credentials){
        UserResponse userResponse = userService.login(credentials);
        return ResponseEntity.ok(userResponse);
    }
}
