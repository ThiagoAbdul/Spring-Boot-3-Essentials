package com.estudos.springframework.client;

import com.estudos.springframework.domain.Anime;
import com.estudos.springframework.mapper.AnimeMapper;
import com.estudos.springframework.dto.AnimeResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {
        String url = "http://localhost:8080/animes/{id}";
        var restTemplate = new RestTemplate();
        ResponseEntity<AnimeResponse> animeResponseEntity = restTemplate.getForEntity(url, AnimeResponse.class, 1);
        log.info(animeResponseEntity);

        url = "http://localhost:8080/animes/all";
        AnimeResponse[] animes = restTemplate.getForObject(url, AnimeResponse[].class);
        log.info(Arrays.toString(animes));

        ResponseEntity<CollectionModel<AnimeResponse>> animeCollection = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        log.info(animeCollection);

        url = "http://localhost:8080/animes/";
        var animePost = AnimeMapper.INSTANCE.toAnimePostRequestBody(
                Anime.builder()
                .name("Dragon Ball GT")
                .author("Akita Toriyama")
                .build()
        );
        AnimeResponse animeResponse = restTemplate.postForObject(url, animePost, AnimeResponse.class);
        log.info(animeResponse);


        url = "http://localhost:8080/animes/{id}";
        if(Objects.nonNull(animeResponse)){
            HttpStatus statusCode = restTemplate.exchange
                            (url, HttpMethod.DELETE, null, Void.class, animeResponse.getId())
                    .getStatusCode();
            log.info(statusCode);
        }

        var animePut = AnimeMapper.INSTANCE.toAnimePutRequestBody(
                Anime.builder()
                        .name("Pokémon")
                        .author("Satoshi Tajiri")
                        .build()
        );
        animeResponse = restTemplate.exchange(
                url, HttpMethod.PUT, new HttpEntity<>(animePut, createJsonHeader()), AnimeResponse.class, 17L)
                .getBody();
        log.info(animeResponse);

    }

    static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
