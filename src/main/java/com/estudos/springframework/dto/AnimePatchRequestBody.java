package com.estudos.springframework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AnimePatchRequestBody {
    @Schema(description = "Name of anime can be omitted in this case")
    private String name;
    private String author;
}
