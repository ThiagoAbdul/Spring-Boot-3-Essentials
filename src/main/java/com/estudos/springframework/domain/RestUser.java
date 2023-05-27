package com.estudos.springframework.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class RestUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Column(length = 50, nullable = false)
    @NotBlank(message = "Username cannot be null or blank")
    private String username;
    @Column(nullable = false)
    @NotBlank(message = "Password cannot be null or blank")
    private String password;
    @Column(length = 25)
    private String authorities;

}
