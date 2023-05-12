package com.estudos.springinitializr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Anime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "The anime cannot be empty")
    private String name;

    private String autor;

    public Anime(String name){
        super();
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Anime){
            Anime anime = (Anime) o;
            return this.getId().equals(anime.getId());
        }
        return false;
    }


}
