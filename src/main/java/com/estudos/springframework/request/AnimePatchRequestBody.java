package com.estudos.springframework.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AnimePatchRequestBody {
    private String name;
    private String author;
}
