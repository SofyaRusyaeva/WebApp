package com.example.WebApp.controller;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.AuthDto;
import com.example.WebApp.dto.JwtResponseDto;
import com.example.WebApp.dto.RefreshTokenDto;
import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.model.RefreshToken;
import com.example.WebApp.model.Users;
import com.example.WebApp.service.AuthService;
import com.example.WebApp.service.BlackListService;
import com.example.WebApp.service.CustomUserDetailsService;
import com.example.WebApp.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    AuthService authService;
    RefreshTokenService refreshTokenService;
    BlackListService blackListService;

    JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsersDto userDto) {
        return ResponseEntity.ok(authService.save(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody AuthDto authDto) {
        return ResponseEntity.ok(authService.authenticate(authDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDto> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        return ResponseEntity.ok(refreshTokenService.refresh(refreshTokenDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenDto refreshTokenDto,  @RequestHeader("Authorization") String authHeader) {
        refreshTokenService.findByToken(refreshTokenDto.getRefreshToken())
                .ifPresent(refreshTokenService::delete);
        String accessToken = authHeader.substring(7);
        blackListService.addToBlacklistToken(accessToken, jwtProvider.extractExpiration(accessToken));
        return ResponseEntity.ok().build();
    }


//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }
//
//    @GetMapping("/register")
//    public String registerPage() {
//        return "register";
//    }
}
