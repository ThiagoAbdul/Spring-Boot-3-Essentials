package com.estudos.springinitializr.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AnimePostRequestBody {
    private String name;
    private String autor;
}
