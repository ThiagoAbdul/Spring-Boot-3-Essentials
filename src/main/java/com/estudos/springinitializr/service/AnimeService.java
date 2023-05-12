package com.estudos.springinitializr.service;

import com.estudos.springinitializr.domain.Anime;
import com.estudos.springinitializr.exception.InvalidIdException;
import com.estudos.springinitializr.exception.ResourceNotFoundException;
import com.estudos.springinitializr.mapper.AnimeMapper;
import com.estudos.springinitializr.repository.AnimeRepository;
import com.estudos.springinitializr.request.AnimePostRequestBody;
import com.estudos.springinitializr.request.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnimeService {

    private final AnimeRepository repository;
    public List<Anime> listAll(){
        return repository.findAll();
    }

    public Anime findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Anime.class));
    }


    private boolean isValidId(Long id){
        return id >= 0;
    }

    public Anime save(AnimePostRequestBody animePostRequestBody){
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePostRequestBody);
        return repository.save(anime);
    }

    public void delete(Long id) throws InvalidIdException {
        if(isValidId(id)){
            repository.deleteById(id);
        }
        else{
            throw new InvalidIdException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Anime replace(Long id, AnimePutRequestBody animePutRequestBody) throws ResourceNotFoundException {
        if(repository.findById(id).isEmpty()){
            throw new ResourceNotFoundException(Anime.class);
        }
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(id);
        return repository.save(anime);
    }
}
