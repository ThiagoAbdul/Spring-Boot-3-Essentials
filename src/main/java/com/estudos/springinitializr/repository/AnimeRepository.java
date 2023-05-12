package com.estudos.springinitializr.repository;

import com.estudos.springinitializr.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

    List<Anime> findAllByName(String name);

}
