package com.estudos.springframework.repository;

import com.estudos.springframework.domain.Anime;

import com.estudos.springframework.util.AnimeCreator;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;

import java.util.Optional;


@DataJpaTest
@DisplayName("Tests for AnimeRepository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;
    private Anime animeSaved;
    @BeforeEach
    void setup(){
        animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    }

    @Test
    @DisplayName("Save a anime when successful")
    void saveTest(){
        var animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        var animeSavedNow = animeRepository.save(animeToBeSaved);
        Assertions.assertThat(animeSavedNow).isNotNull();
        Assertions.assertThat(animeSavedNow.getId()).isNotNull();
        Assertions.assertThat(animeToBeSaved).isEqualTo(animeSavedNow);
    }

    @Test
    @DisplayName("Update a anime when successful")
    void updateTest(){
        animeSaved.setName("Naruto");
        Anime animeUpdated = animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated)
                .isNotNull();
        Assertions.assertThat(animeUpdated.getId())
                .isNotNull()
                .isEqualTo(animeSaved.getId());
        Assertions.assertThat(animeUpdated.getName())
                .isEqualTo(animeSaved.getName())
                .isEqualTo("Naruto");
    }

    @Test
    @DisplayName("Deletes a anime when successful")
    void deleteTest(){
        long id = animeSaved.getId();
        animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = animeRepository.findById(id);
        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find the anime by name")
    void findByNameTest(){
        var animeList = animeRepository.findAllByName("Bleach");
        Assertions.assertThat(animeList).isNotEmpty();
        animeList.forEach(
                anime -> Assertions.assertThat(anime.getName()).isEqualTo("Bleach")
        );
    }

    @Test
    @DisplayName("Find the anime by wrong name - Expected a empty list")
    void findByWrongNameTest(){
        var animeList = animeRepository.findAllByName("Bliti");
        Assertions.assertThat(animeList).isEmpty();
    }



    @Test
    @DisplayName("Test if allows to save anime with empty name")
    void saveAnimeWithEmptyNameTest(){
        var anime = Anime.builder()
                .author("Test")
                .build();
//        Assertions.assertThatThrownBy(() -> animeRepository.save(anime))
//                .isInstanceOf(ConstraintViolationException.class);
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> animeRepository.save(anime))
                .withMessageContaining("The anime name cannot be blank");

    }

}