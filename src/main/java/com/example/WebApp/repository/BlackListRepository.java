package com.example.WebApp.repository;

import com.example.WebApp.model.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
    boolean existsByToken(String token);
    void deleteByExpiryDateBefore(Instant now);
}
