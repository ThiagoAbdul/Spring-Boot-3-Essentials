package com.estudos.springframework.util;

import com.estudos.springframework.request.AnimeView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

public class AnimeViewCreator {

    public static AnimeView createAnimeView(){
        return new AnimeView(7L, "Naruto", "Masashi Kishimoto");
    }

    public static List<AnimeView> createListOfAnimeView(){
        return List.of(
                new AnimeView(1L, "Bleach", "Tite Kubo"),
                new AnimeView(2L, "One Piece", null),
                new AnimeView(7L, "Naruto", "Masashi Kishimoto")
        );
    }
    public static Page<AnimeView> createPageOfAnimeView(){
        return new PageImpl<>(createListOfAnimeView());
    }

}
