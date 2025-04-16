package com.example.WebApp.controller;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.PasswordDto;
import com.example.WebApp.dto.UserUpdateDto;
import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.model.Users;
import com.example.WebApp.service.UsersService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop/users")
public class UsersController {

    UsersService usersService;
    JwtProvider jwtProvider;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<Users>> getUsers() {
        return ResponseEntity.ok(usersService.findAll());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public  ResponseEntity<Users> getMe() {
        Long userId = jwtProvider.getCurrentUserId();
        return ResponseEntity.ok(usersService.findById(userId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public  ResponseEntity<Users> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(usersService.findById(userId));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me/edit")
    public ResponseEntity<Users> updateUsers(@RequestBody UserUpdateDto users) {
        usersService.update(users);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/me/edit/password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto) {
        usersService.updatePassword(passwordDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUsers () {
        Long userId = jwtProvider.getCurrentUserId();
        usersService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
