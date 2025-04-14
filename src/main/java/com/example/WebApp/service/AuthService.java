package com.example.WebApp.service;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.AuthDto;
import com.example.WebApp.dto.CartDto;
import com.example.WebApp.dto.JwtResponseDto;
import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.exeption.DuplicateException;
import com.example.WebApp.exeption.InvalidCredentialsException;
import com.example.WebApp.exeption.ObjectNotFoundException;
import com.example.WebApp.exeption.ObjectSaveException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.RefreshToken;
import com.example.WebApp.model.Users;
import com.example.WebApp.repository.RefreshTokenRepository;
import com.example.WebApp.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    Mapper mapper;
    PasswordEncoder passwordEncoder;
    UsersRepository usersRepository;
    CartService cartService;
    JwtProvider jwtProvider;
    UserDetailsService userDetailsService;
    AuthenticationManager authenticationManager;
    RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Users save(UsersDto userDto) {
        Users user = mapper.toUsers(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            usersRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateException(String.format("email %s already exists", userDto. getEmail()));
        } catch (Exception e) {
            throw new ObjectSaveException("Error saving user");
        }
        cartService.save(new CartDto(BigDecimal.ZERO, user.getUserId()));
        return user;
    }

//    public String authenticate(AuthDto authDto) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword())
//            );
//        } catch (AuthenticationException e) {
//            throw new InvalidCredentialsException("Invalid email or password");
//        }
//        Users user = usersRepository.findByEmail(authDto.getEmail())
//                        .orElseThrow(() -> new ObjectNotFoundException("User not found"));
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(authDto.getEmail());
//        return jwtProvider.generateAccessToken(userDetails, user.getUserId());
//    }

    public JwtResponseDto authenticate(AuthDto authDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users user = usersRepository.findByEmail(authDto.getEmail())
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        String accessToken = jwtProvider.generateAccessToken(userDetails, user.getUserId());
        String refreshToken = jwtProvider.generateRefreshToken(userDetails.getUsername());
        refreshTokenRepository.save(new RefreshToken(null, refreshToken, user, Instant.now().plusMillis(86400000)));

        return new JwtResponseDto(accessToken, refreshToken);
    }

    public String refreshAccessToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtProvider.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Users user = usersRepository.findByEmail(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        return jwtProvider.generateAccessToken(userDetails, user.getUserId());
    }
}
