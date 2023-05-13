package com.estudos.springframework.service;

import com.estudos.springframework.domain.Anime;
import com.estudos.springframework.exceptions.BadRequestException;
import com.estudos.springframework.exceptions.ResourceNotFoundException;
import com.estudos.springframework.mapper.AnimeMapper;
import com.estudos.springframework.repository.AnimeRepository;
import com.estudos.springframework.request.AnimePatchRequestBody;
import com.estudos.springframework.request.AnimePostRequestBody;
import com.estudos.springframework.request.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public List<Anime> findAllByName(String name){
        return repository.findAllByName(name);
    }


    private boolean isValidId(Long id){
        return id > 0;
    }

    public Anime save(AnimePostRequestBody animePostRequestBody){
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePostRequestBody);
        return repository.save(anime);
    }

    public void delete(Long id) throws BadRequestException {
        if(isValidId(id)){
            try{
                repository.deleteById(id);
            }
            catch (Exception e){
                throw new BadRequestException(e.getMessage());
            }
        }
        else{
            throw new BadRequestException("Anime not found");
        }
    }

    @Transactional
    public Anime update(Long id, AnimePatchRequestBody animePatchRequestBody) throws BadRequestException {
        Anime anime = repository.findById(id).orElseThrow(() -> new BadRequestException("Anime not found"));
        updateAnime(anime, animePatchRequestBody);
        return anime;
    }

    private void updateAnime(Anime originalAnime, AnimePatchRequestBody updatedAnime){
        if(Objects.nonNull(updatedAnime.getName())){
            originalAnime.setName(updatedAnime.getName());
        }
        if(Objects.nonNull(updatedAnime.getAuthor())){
            originalAnime.setAuthor(updatedAnime.getAuthor());
        }
    }

    public Anime replace(Long id, AnimePutRequestBody animePutRequestBody) throws BadRequestException{
        repository.findById(id).orElseThrow(() -> new BadRequestException("Anime not found"));
        Anime animeUpdated = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        animeUpdated.setId(id);
        return repository.save(animeUpdated);
    }

    public Page<Anime> listAll(Pageable page) {
        // Sort sort = Sort.by("name").descending();
        // Pageable pageable = PageRequest.of(0, 5, sort);
        return repository.findAll(page);
    }
}
