package com.example.WebApp.service;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.JwtResponseDto;
import com.example.WebApp.dto.RefreshTokenDto;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.model.RefreshToken;
import com.example.WebApp.model.Users;
import com.example.WebApp.repository.RefreshTokenRepository;
import com.example.WebApp.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenService {
    final RefreshTokenRepository refreshTokenRepository;
    final JwtProvider jwtProvider;
    final CustomUserDetailsService userDetailsService;

    @Transactional
    public JwtResponseDto refresh(RefreshTokenDto refreshTokenDto) {
        String requestRefreshToken = refreshTokenDto.getRefreshToken();
        return findByToken(requestRefreshToken)
                .map(this::verifyExpiration)
                .map(existingToken -> {
                    delete(existingToken);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(existingToken.getUser().getEmail());
                    String refreshToken = jwtProvider.generateRefreshToken(existingToken.getUser().getEmail());
                    String accessToken = jwtProvider.generateAccessToken(userDetails, existingToken.getUser().getUserId());
                    return new JwtResponseDto(accessToken, refreshToken);
                })
                .orElseThrow(() -> new ObjectNotFoundException("Refresh token not found"));
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void delete (RefreshToken token){
        refreshTokenRepository.delete(token);
    }

    @Scheduled(fixedRate = 86400000)
    public void cleanupExpiredTokens() {
        refreshTokenRepository.deleteByExpiryDateBefore(Instant.now());
    }
}