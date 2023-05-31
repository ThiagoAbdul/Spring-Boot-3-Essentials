### A REST Api implemented in *Spring* and *MySQL* in which i used:

- #### HATEOAS

- #### Authentication

- #### Docker

- #### Prometheus

- #### Spring Docs

- #### Tests

- #### Pagination

### Consuming the Api:

```
anime = {"name": "Bleach", "author": "Tite Kubo"}
curl -X POST http://localhost:8080/animes -u dev -H "Content-Type: application/json" -d $anime -v
```

### See on the Docker [Hub](https://hub.docker.com/repository/docker/thiagoabdul/spring-essentials/general)

