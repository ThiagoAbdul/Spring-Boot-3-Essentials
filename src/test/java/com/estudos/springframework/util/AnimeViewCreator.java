package com.estudos.springframework.util;

import com.estudos.springframework.dto.AnimeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class AnimeViewCreator {

    public static AnimeResponse createAnimeView(){
        return new AnimeResponse(7L, "Naruto", "Masashi Kishimoto");
    }

    public static List<AnimeResponse> createListOfAnimeView(){
        return List.of(
                new AnimeResponse(1L, "Bleach", "Tite Kubo"),
                new AnimeResponse(2L, "One Piece", null),
                new AnimeResponse(7L, "Naruto", "Masashi Kishimoto")
        );
    }
    public static Page<AnimeResponse> createPageOfAnimeView(){
        return new PageImpl<>(createListOfAnimeView());
    }

}
