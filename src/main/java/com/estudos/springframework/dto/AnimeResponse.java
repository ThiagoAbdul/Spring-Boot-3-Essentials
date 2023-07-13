package com.estudos.springframework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimeResponse extends RepresentationModel<AnimeResponse> {
    private long id;
    private String name;
    private String author;
}
