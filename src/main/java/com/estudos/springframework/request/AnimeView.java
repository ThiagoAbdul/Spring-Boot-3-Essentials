package com.estudos.springframework.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnimeView {
    private long id;
    private String name;
    private String author;
}
