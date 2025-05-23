package com.example.WebApp.service;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.AuthDto;
import com.example.WebApp.dto.JwtResponseDto;
import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.exeption.DuplicateException;
import com.example.WebApp.exeption.InvalidValueException;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.RefreshToken;
import com.example.WebApp.model.Users;
import com.example.WebApp.repository.RefreshTokenRepository;
import com.example.WebApp.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    Mapper mapper;
    PasswordEncoder passwordEncoder;
    UsersRepository usersRepository;
    JwtProvider jwtProvider;
    AuthenticationManager authenticationManager;
    RefreshTokenRepository refreshTokenRepository;
    CartService cartService;

    @Value("${jwt.refresh-expiration}")
    @NonFinal
    private long refreshExpiration;

    @Transactional
    public Users save(UsersDto userDto) {
        List<String> missingFields = new ArrayList<>();

        if (userDto.getEmail().isEmpty()) missingFields.add("email");
        if (userDto.getPassword().isEmpty()) missingFields.add("пароль");
        if (userDto.getUserName().isEmpty()) missingFields.add("имя пользователя");
        if (!missingFields.isEmpty())
            throw new InvalidValueException(
                    String.format("Поля %s обязательны", String.join(", ", missingFields)), "register");

        Users user = mapper.toUsers(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (usersRepository.findByEmail(userDto.getEmail()).isPresent())
            throw new DuplicateException(String.format("Email %s уже существует", userDto. getEmail()), "register");
        Users newUser = usersRepository.save(user);
        cartService.save(user.getUserId());
        return newUser;
    }

    public JwtResponseDto authenticate(AuthDto authDto) {
        List<String> missingFields = new ArrayList<>();
        if (authDto.getEmail().isEmpty()) missingFields.add("email");
        if (authDto.getPassword().isEmpty()) missingFields.add("пароль");
        if (!missingFields.isEmpty())
            throw new InvalidValueException(
                String.format("Поля %s обязательны", String.join(", ", missingFields)), "login");
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users user = usersRepository.findByEmail(authDto.getEmail())
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        String accessToken = jwtProvider.generateAccessToken(userDetails, user.getUserId());
        String refreshToken = jwtProvider.generateRefreshToken(userDetails.getUsername());
        refreshTokenRepository.save(new RefreshToken(null, refreshToken, user, Instant.now().plusMillis(refreshExpiration)));

        return new JwtResponseDto(accessToken, refreshToken);
    }

//    public String refreshAccessToken(String refreshToken) {
//        if (!jwtProvider.validateToken(refreshToken)) {
//            throw new RuntimeException("Invalid refresh token");
//        }
//
//        String username = jwtProvider.extractUsername(refreshToken);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        Users user = usersRepository.findByEmail(username)
//                .orElseThrow(() -> new ObjectNotFoundException("User not found"));
//
//        return jwtProvider.generateAccessToken(userDetails, user.getUserId());
//    }
}
