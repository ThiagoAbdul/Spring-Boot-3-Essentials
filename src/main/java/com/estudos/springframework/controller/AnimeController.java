package com.estudos.springframework.controller;

import com.estudos.springframework.domain.Anime;
import com.estudos.springframework.exceptions.BadRequestException;
import com.estudos.springframework.exceptions.ResourceNotFoundException;
import com.estudos.springframework.request.AnimePatchRequestBody;
import com.estudos.springframework.request.AnimePostRequestBody;
import com.estudos.springframework.request.AnimePutRequestBody;
import com.estudos.springframework.service.AnimeService;
import com.estudos.springframework.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    public ResponseEntity<Page<Anime>> listAll(Pageable page){
        log.info(dateUtil.formatLocalDateTimeToSQLDate(LocalDateTime.now()));
        return ResponseEntity.ok(service.listAll(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
        try{
            return ResponseEntity.ok(service.findById(id));
        }
        catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseEntity<List<Anime>> findAllByname(@RequestParam(required = false) String name){
        return ResponseEntity.ok(service.findAllByName(name));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody anime){
        Anime animeSaved = service.save(anime);
        return new ResponseEntity<>(animeSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) throws BadRequestException{
        service.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Anime> update(@PathVariable long id, @RequestBody AnimePatchRequestBody anime)
                                                                            throws BadRequestException{
        return ResponseEntity.ok(service.update(id, anime));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anime> replace(@PathVariable long id, @Valid @RequestBody AnimePutRequestBody anime)
                                                                                throws BadRequestException{
        return ResponseEntity.ok(service.replace(id, anime));
    }
}
