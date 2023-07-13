package com.estudos.springframework.service;

import com.estudos.springframework.domain.Anime;
import com.estudos.springframework.exceptions.BadRequestException;
import com.estudos.springframework.exceptions.ResourceNotFoundException;
import com.estudos.springframework.mapper.AnimeMapper;
import com.estudos.springframework.repository.AnimeRepository;
import com.estudos.springframework.dto.AnimePatchRequestBody;
import com.estudos.springframework.dto.AnimePostRequestBody;
import com.estudos.springframework.dto.AnimePutRequestBody;
import com.estudos.springframework.dto.AnimeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnimeService {

    private final AnimeRepository repository;
    private final AnimeMapper animeMapper = AnimeMapper.INSTANCE;
    @Transactional(readOnly = true)
    public List<AnimeResponse> listAll(){
        return repository.findAll()
                .stream().map(animeMapper::toAnimeView)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnimeResponse findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id)
                .map(animeMapper::toAnimeView)
                .orElseThrow(() -> new ResourceNotFoundException(Anime.class));
    }

    public List<AnimeResponse> findAllByName(String name){
        return repository.findAllByName(name)
                .stream().map(animeMapper::toAnimeView)
                .collect(Collectors.toList());
    }


    private boolean isValidId(Long id){
        return id > 0;
    }

    public AnimeResponse save(AnimePostRequestBody animePostRequestBody){
        Anime anime = animeMapper.toAnime(animePostRequestBody);
        repository.save(anime);
        return animeMapper.toAnimeView(anime);
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
    public AnimeResponse update(Long id, AnimePatchRequestBody animePatchRequestBody) throws BadRequestException {
        Anime anime = repository.findById(id).orElseThrow(() -> new BadRequestException("Anime not found"));
        updateAnime(anime, animePatchRequestBody);
        return animeMapper.toAnimeView(anime);
    }

    private void updateAnime(Anime originalAnime, AnimePatchRequestBody updatedAnime){
        if(Objects.nonNull(updatedAnime.getName())){
            originalAnime.setName(updatedAnime.getName());
        }
        if(Objects.nonNull(updatedAnime.getAuthor())){
            originalAnime.setAuthor(updatedAnime.getAuthor());
        }
    }

    public AnimeResponse replace(Long id, AnimePutRequestBody animePutRequestBody) throws BadRequestException{
        repository.findById(id).orElseThrow(() -> new BadRequestException("Anime not found"));
        Anime animeUpdated = animeMapper.toAnime(animePutRequestBody);
        animeUpdated.setId(id);
        repository.save(animeUpdated);
        return animeMapper.toAnimeView(animeUpdated);
    }

    public Page<AnimeResponse> listAll(Pageable page) {
        // Sort sort = Sort.by("name").descending();
        // Pageable pageable = PageRequest.of(0, 5, sort);
        return repository.findAll(page).map(animeMapper::toAnimeView);
    }
}
