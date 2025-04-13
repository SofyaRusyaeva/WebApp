package com.example.WebApp.service;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.AuthDto;
import com.example.WebApp.dto.CartDto;
import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.exeption.DuplicateException;
import com.example.WebApp.exeption.InvalidCredentialsException;
import com.example.WebApp.exeption.ObjectSaveException;
import com.example.WebApp.mapper.Mapper;
import com.example.WebApp.model.Users;
import com.example.WebApp.repository.UsersRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

//    @Transactional
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

    public String authenticate(AuthDto authDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authDto.getEmail());
        return jwtProvider.generateToken(userDetails.getUsername());
    }
}
