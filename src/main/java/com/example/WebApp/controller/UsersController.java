package com.example.WebApp.controller;

import com.example.WebApp.config.JwtProvider;
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

//    @PostMapping()
//    public ResponseEntity<Users> addUsers(@Valid @RequestBody UsersDto users) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.save(users));
//    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{userId}")
    public ResponseEntity<Users> updateUsers(@Valid @RequestBody UsersDto users, @PathVariable Long userId) {
        return ResponseEntity.ok(usersService.update(users, userId));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUsers () {
        Long userId = jwtProvider.getCurrentUserId();
        usersService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
