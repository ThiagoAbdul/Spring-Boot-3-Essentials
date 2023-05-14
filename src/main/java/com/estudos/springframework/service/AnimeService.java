package com.estudos.springframework.service;

import com.estudos.springframework.domain.Anime;
import com.estudos.springframework.exceptions.BadRequestException;
import com.estudos.springframework.exceptions.ResourceNotFoundException;
import com.estudos.springframework.mapper.AnimeMapper;
import com.estudos.springframework.repository.AnimeRepository;
import com.estudos.springframework.request.AnimePatchRequestBody;
import com.estudos.springframework.request.AnimePostRequestBody;
import com.estudos.springframework.request.AnimePutRequestBody;
import com.estudos.springframework.request.AnimeView;
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
    public List<AnimeView> listAll(){
        return repository.findAll()
                .stream().map(animeMapper::toAnimeView)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnimeView findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id)
                .map(animeMapper::toAnimeView)
                .orElseThrow(() -> new ResourceNotFoundException(Anime.class));
    }

    public List<AnimeView> findAllByName(String name){
        return repository.findAllByName(name)
                .stream().map(animeMapper::toAnimeView)
                .collect(Collectors.toList());
    }


    private boolean isValidId(Long id){
        return id > 0;
    }

    public AnimeView save(AnimePostRequestBody animePostRequestBody){
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

    public AnimeView replace(Long id, AnimePutRequestBody animePutRequestBody) throws BadRequestException{
        repository.findById(id).orElseThrow(() -> new BadRequestException("Anime not found"));
        Anime animeUpdated = animeMapper.toAnime(animePutRequestBody);
        animeUpdated.setId(id);
        repository.save(animeUpdated);
        return animeMapper.toAnimeView(animeUpdated);
    }

    public Page<AnimeView> listAll(Pageable page) {
        // Sort sort = Sort.by("name").descending();
        // Pageable pageable = PageRequest.of(0, 5, sort);
        return repository.findAll(page).map(animeMapper::toAnimeView);
    }
}
