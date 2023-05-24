package com.estudos.springframework.util;

import com.estudos.springframework.domain.Anime;
import com.estudos.springframework.request.AnimePostRequestBody;
import com.estudos.springframework.request.AnimeView;

import java.util.List;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved(){
        return Anime.builder()
                .name("Bleach")
                .author("Tite Kubo")
                .build();
    }



}
