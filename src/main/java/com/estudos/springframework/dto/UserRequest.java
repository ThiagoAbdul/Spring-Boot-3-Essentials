package com.estudos.springframework.dto;

import com.estudos.springframework.security.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class UserRequest {
    @NotNull
    @Email
    private String email;
    @NotNull
    @Length(min = 8)
    private String password;
    @NotNull
    private Role role;
}
