package com.estudos.springframework.repository;

import com.estudos.springframework.domain.RestUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestUserRepository extends JpaRepository<RestUser, Long> {
    RestUser findByUsername(String username);
}
