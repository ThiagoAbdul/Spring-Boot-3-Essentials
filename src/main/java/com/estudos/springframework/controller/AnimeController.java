package com.estudos.springframework.controller;

import com.estudos.springframework.exceptions.BadRequestException;
import com.estudos.springframework.exceptions.ResourceNotFoundException;
import com.estudos.springframework.request.AnimePatchRequestBody;
import com.estudos.springframework.request.AnimePostRequestBody;
import com.estudos.springframework.request.AnimePutRequestBody;
import com.estudos.springframework.request.AnimeView;
import com.estudos.springframework.service.AnimeService;
import com.estudos.springframework.util.DateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/animes")
@RequiredArgsConstructor
@Log4j2
public class AnimeController {

    private final DateUtil dateUtil;
    private final AnimeService service;

    @GetMapping({"/", ""})
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @Operation(summary = "List all animes paginated", description = "Default size is 10")
    public ResponseEntity<CollectionModel<AnimeView>> listPageable(@ParameterObject Pageable page){
        //log.info(dateUtil.formatLocalDateTimeToSQLDate(LocalDateTime.now()));
        Page<AnimeView> animeViews = service.listAll(page);
        return ResponseEntity.ok(hateoasOf(animeViews));
    }

    @GetMapping("/public")
    public ResponseEntity<String> home(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok("Animes API");
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<AnimeView>> listAll(){
        List<AnimeView> animeViews = service.listAll();
        return ResponseEntity.ok(hateoasOf(animeViews));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimeView> findById(@PathVariable long id){
        try{
            AnimeView animeView = service.findById(id);
            return ResponseEntity.ok(hateoasOf(animeView));
        }
        catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseEntity<CollectionModel<AnimeView>> findAllByname(@RequestParam(required = false) String name){
        List<AnimeView> animeViews = service.findAllByName(name);
        return ResponseEntity.ok(hateoasOf(animeViews));
    }

    @PostMapping
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity<AnimeView> save(@RequestBody @Valid AnimePostRequestBody anime){
        AnimeView savedAnime = service.save(anime);
        return new ResponseEntity<>(hateoasWith2Links(savedAnime), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "If try to delete as USER authority"),
            @ApiResponse(responseCode = "400", description = "If anime id don't exists")
    })
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long id) throws BadRequestException{
        service.delete(id);
        log.info("User {} deleted anime with id {}", userDetails.getUsername(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AnimeView> update(@PathVariable long id, @RequestBody AnimePatchRequestBody anime)
                                                                            throws BadRequestException{
        AnimeView updatedAnime = service.update(id, anime);
        return ResponseEntity.ok(hateoasWith2Links(updatedAnime));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimeView> replace(@PathVariable long id, @Valid @RequestBody AnimePutRequestBody anime)
                                                                                throws BadRequestException{
        AnimeView replacedAnime = service.replace(id, anime);
        return ResponseEntity.ok(hateoasWith2Links(replacedAnime));
    }

    private AnimeView hateoasOf(AnimeView animeView){
        animeView.add(linkTo(AnimeController.class).withSelfRel());
        return animeView;
    }

    private AnimeView hateoasWith2Links(AnimeView animeView){
        animeView.add(linkTo(
                methodOn(AnimeController.class).findById(animeView.getId())
        ).withSelfRel());
        return hateoasOf(animeView);
    }

    private CollectionModel<AnimeView> hateoasOf(Iterable<AnimeView> animeViews){
        for (var animeView: animeViews){
            animeView.add(linkTo(
                            methodOn(AnimeController.class).findById(animeView.getId())
                    ).withSelfRel()
            );
        }
        return CollectionModel.of(animeViews);
    }

}
