package com.estudos.springframework.util;

import com.estudos.springframework.domain.Anime;
import com.estudos.springframework.request.AnimePostRequestBody;
import com.estudos.springframework.request.AnimeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved(){
        return Anime.builder()
                .name("Bleach")
                .author("Tite Kubo")
                .build();
    }

    public static Anime createSavedAnime(){
        return Anime.builder()
                .id(7L)
                .name("Naruto")
                .author("Masashi Kishimoto")
                .build();
    }

    public static List<Anime> createListOfAnimes(){
        return new ArrayList<>(List.of(
                new Anime(1L, "Bleach", "Tite Kubo"),
                new Anime(2L, "DBZ", "Akita Toriyama"),
                new Anime(3L, "Boruto", "Masashi Kishimoto"),
                new Anime(4L, "Berserk", null)
        ));
    }

}
