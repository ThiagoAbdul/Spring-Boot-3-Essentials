package com.estudos.springframework.integration;

import com.estudos.springframework.domain.Anime;
import com.estudos.springframework.repository.AnimeRepository;
import com.estudos.springframework.request.AnimeView;
import com.estudos.springframework.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplateAuthorityUser")
    private TestRestTemplate testRestTemplateUser;

    @Autowired
    @Qualifier(value = "testRestTemplateAuthorityAdmin")
    private TestRestTemplate testRestTemplateAdmin;
    @Autowired
    private AnimeRepository animeRepository;

    @TestConfiguration
    static class Config{
        @Bean(name = "testRestTemplateAuthorityUser")
        public TestRestTemplate testRestTemplateAuthorityUserCreator(){
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .basicAuthentication("dev", "strong_password");
            return new TestRestTemplate(restTemplateBuilder);
        }
        @Bean(name = "testRestTemplateAuthorityAdmin")
        public TestRestTemplate testRestTemplateAuthorityAdminCreator(){
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .basicAuthentication("admin", "admin_password");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("Test the method getRootUri")
    void getRootUri(){
        String rootUri = getRootUri("/all");
        Assertions.assertThat(rootUri)
                .isEqualTo("http://localhost:8080/animes/all");

    }
    @Test
    void listAllAsUserAuthorityTest(){
        final String URL = getRootUri("/all");
        ResponseEntity<CollectionModel<AnimeView>> responseAnime = testRestTemplateUser.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CollectionModel<AnimeView>>() {
                }
        );
        Assertions.assertThat(responseAnime)
                .isNotNull();
        Assertions.assertThat(responseAnime.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseAnime.getBody())
                .isNotNull();

    }

    @Test
    void listAllAsAdminAuthorityTest(){
        final String URL = getRootUri("/all");
        ResponseEntity<CollectionModel<AnimeView>> responseAnime = testRestTemplateAdmin.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CollectionModel<AnimeView>>() {
                }
        );
        Assertions.assertThat(responseAnime)
                .isNotNull();
        Assertions.assertThat(responseAnime.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseAnime.getBody())
                .isNotNull();
    }

    @Test
    @DisplayName("deleteAsUserTest: Expected 403 Forbidden")
    void deleteAsUserTest(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        final String URL = getRootUri("/" + savedAnime.getId());
        ResponseEntity<Void> responseEntity = testRestTemplateUser.exchange(URL,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isInstanceOf(HttpStatus.class)
                .isEqualTo(HttpStatus.FORBIDDEN);
    }

    private String getRootUri(String path){
        return "http://localhost:8080/animes" +
                path;
    }


}
