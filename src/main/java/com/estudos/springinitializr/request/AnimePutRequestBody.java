package com.estudos.springinitializr.request;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AnimePutRequestBody {
    @NotEmpty(message = "The anime name cannot be empty")
    private String name;

    @NotEmpty(message = "The author of anime cannot be empty")
    private String author;
}
