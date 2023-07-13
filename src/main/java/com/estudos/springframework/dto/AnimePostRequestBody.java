package com.estudos.springframework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AnimePostRequestBody {
    @NotBlank(message = "The anime's name cannot be empty")
    @Schema(example = "Dragon Ball Z")
    private String name;
    private String author;


}
