package com.example.WebApp.controller;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.AuthDto;
import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.model.Role;
import com.example.WebApp.model.Users;
import com.example.WebApp.repository.UsersRepository;
import com.example.WebApp.service.AuthService;
import com.example.WebApp.service.CustomUserDetailsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {
    AuthenticationManager authenticationManager;
    CustomUserDetailsService userDetailsService;
    JwtProvider jwtProvider;
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsersDto userDto) {
        return ResponseEntity.ok(authService.save(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDto authDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(authDto.getEmail());
        String token = jwtProvider.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(token);
    }
}
