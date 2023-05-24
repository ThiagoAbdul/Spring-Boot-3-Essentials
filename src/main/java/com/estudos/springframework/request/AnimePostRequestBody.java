package com.estudos.springframework.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AnimePostRequestBody {
    @NotBlank(message = "The anime name cannot be empty")
    private String name;
    private String author;


}
