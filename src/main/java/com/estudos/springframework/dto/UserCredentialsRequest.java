package com.estudos.springframework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserCredentialsRequest {
    @Email
    @NotNull
    private String email;

    @NotNull
    @Length(min = 8)
    private String password;
}
