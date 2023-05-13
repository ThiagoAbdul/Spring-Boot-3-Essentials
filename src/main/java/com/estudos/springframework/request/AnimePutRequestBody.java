package com.estudos.springframework.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AnimePutRequestBody {
    @NotBlank(message = "The anime name cannot be empty")
    private String name;

    @NotBlank(message = "The author of anime cannot be empty")
    private String author;
}
