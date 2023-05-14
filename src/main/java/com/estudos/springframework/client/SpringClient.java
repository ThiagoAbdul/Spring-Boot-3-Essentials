package com.estudos.springframework.client;

import com.estudos.springframework.domain.Anime;
import com.estudos.springframework.mapper.AnimeMapper;
import com.estudos.springframework.request.AnimePostRequestBody;
import com.estudos.springframework.request.AnimeView;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {
        String url = "http://localhost:8080/animes/{id}";
        var restTemplate = new RestTemplate();
        ResponseEntity<AnimeView> animeResponseEntity = restTemplate.getForEntity(url, AnimeView.class, 1);
        log.info(animeResponseEntity);

        url = "http://localhost:8080/animes/all";
        AnimeView[] animes = restTemplate.getForObject(url, AnimeView[].class);
        log.info(Arrays.toString(animes));

        ResponseEntity<List<AnimeView>> animeList = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        log.info(animeList);

        url = "http://localhost:8080/animes/";
        var animePost = AnimeMapper.INSTANCE.toAnimePostRequestBody(
                Anime.builder()
                .name("Dragon Ball GT")
                .author("Akita Toriyama")
                .build()
        );
        AnimeView animeView = restTemplate.postForObject(url, animePost, AnimeView.class);
        log.info(animeView);


        url = "http://localhost:8080/animes/{id}";
        if(Objects.nonNull(animeView)){
            HttpStatus statusCode = restTemplate.exchange
                            (url, HttpMethod.DELETE, null, Void.class, animeView.getId())
                    .getStatusCode();
            log.info(statusCode);
        }

        var animePut = AnimeMapper.INSTANCE.toAnimePutRequestBody(
                Anime.builder()
                        .name("Pokémon")
                        .author("Satoshi Tajiri")
                        .build()
        );
        animeView = restTemplate.exchange(
                url, HttpMethod.PUT, new HttpEntity<>(animePut, createJsonHeader()), AnimeView.class, 17L)
                .getBody();
        log.info(animeView);

    }

    static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
