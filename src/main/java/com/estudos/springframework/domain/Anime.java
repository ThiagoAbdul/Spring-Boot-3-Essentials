package com.estudos.springframework.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Anime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 50)
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
