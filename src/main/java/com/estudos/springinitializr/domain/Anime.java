package com.estudos.springinitializr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Anime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String author;

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
