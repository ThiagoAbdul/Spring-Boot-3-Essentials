package com.estudos.springinitializr.controller;

import com.estudos.springinitializr.domain.Anime;
import com.estudos.springinitializr.exception.InvalidIdException;
import com.estudos.springinitializr.exception.ResourceNotFoundException;
import com.estudos.springinitializr.request.AnimePostRequestBody;
import com.estudos.springinitializr.request.AnimePutRequestBody;
import com.estudos.springinitializr.service.AnimeService;
import com.estudos.springinitializr.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/animes")
@RequiredArgsConstructor
@Log4j2
public class AnimeController {
    private final DateUtil dateUtil;
    private final AnimeService service;
    @CrossOrigin(origins = {"https://127.0.0.1:4200"})
    @GetMapping({"/", ""})
    public ResponseEntity<List<Anime>> list(){
        log.info(dateUtil.formatLocalDateTimeToSQLDate(LocalDateTime.now()));
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(service.findById(id));
        }
        catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody AnimePostRequestBody anime){
        Anime animeSaved = service.save(anime);
        return new ResponseEntity<>(animeSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            service.delete(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        catch (InvalidIdException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime id not found");
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anime> replace(@PathVariable Long id, @RequestBody AnimePutRequestBody anime){
        try{
            return ResponseEntity.ok(service.replace(id, anime));
        }
        catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
