package com.example.WebApp.controller;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.AuthDto;
import com.example.WebApp.dto.JwtResponseDto;
import com.example.WebApp.dto.RefreshTokenDto;
import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.model.RefreshToken;
import com.example.WebApp.service.AuthService;
import com.example.WebApp.service.BlackListService;
import com.example.WebApp.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

//    @PostMapping("/register")
//    public ResponseEntity<JwtResponseDto> register(@RequestBody UsersDto userDto) {
//        authService.save(userDto);
//        AuthDto authDto = new AuthDto(userDto.getEmail(), userDto.getPassword());
//        return ResponseEntity.ok(authService.authenticate(authDto));
//    }

    @PostMapping("/register")
    public String register(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String username,
            @RequestParam String phone,
            HttpServletResponse response
    ) {
        UsersDto userDto = new UsersDto(username, email, password, phone);
        authService.save(userDto);
        JwtResponseDto jwt = authService.authenticate(new AuthDto(email, password));

        Cookie cookie = new Cookie("access_token", jwt.getAccessToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        Cookie refreshCookie = new Cookie("refresh_token", jwt.getRefreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);
        return "redirect:/";
    }


    @PostMapping("/login")
    public String loginFromForm(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response
    ) {
        JwtResponseDto jwt = authService.authenticate(new AuthDto(email, password));

        Cookie cookie = new Cookie("access_token", jwt.getAccessToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        Cookie refreshCookie = new Cookie("refresh_token", jwt.getRefreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);

        return "redirect:/";
    }


    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDto> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        return ResponseEntity.ok(refreshTokenService.refresh(refreshTokenDto));
    }

    @PostMapping("/logout")
    public String logout(
            HttpServletRequest request,
            HttpServletResponse response,
            @CookieValue(name = "access_token", required = false) String accessToken,
            @CookieValue(name = "refresh_token", required = false) String refreshToken
    ) {

        if (refreshToken != null) {
            refreshTokenService.findByToken(refreshToken)
                    .ifPresent(refreshTokenService::delete);
        }

        if (accessToken != null) {
            blackListService.addToBlacklistToken(accessToken, jwtProvider.extractExpiration(accessToken));
        }

        Cookie cookie = new Cookie("access_token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        Cookie refreshCookie = new Cookie("refresh_token", null);
        refreshCookie.setPath("/");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(0);
        response.addCookie(refreshCookie);

        return "login";
    }
}
