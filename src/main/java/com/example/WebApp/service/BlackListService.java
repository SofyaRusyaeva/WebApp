package com.example.WebApp.service;

import com.example.WebApp.model.BlackList;
import com.example.WebApp.repository.BlackListRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlackListService {

    BlackListRepository blackListRepository;

    public void addToBlacklistToken(String token, Instant expiryDate) {
        BlackList blacklistedToken = new BlackList();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpiryDate(expiryDate);
        blackListRepository.save(blacklistedToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return blackListRepository.existsByToken(token);
    }

    @Scheduled(fixedRate = 86400000)
    @Transactional
    public void cleanupExpiredTokens() {
        blackListRepository.deleteByExpiryDateBefore(Instant.now());
    }
}
