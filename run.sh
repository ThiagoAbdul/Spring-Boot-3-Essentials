#!/bin/bash
docker-compose up -d
bash ./mvnw clean spring-boot:run
