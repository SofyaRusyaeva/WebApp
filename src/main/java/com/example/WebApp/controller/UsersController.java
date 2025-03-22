package com.example.WebApp.controller;

import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.model.Users;
import com.example.WebApp.service.UsersService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop/users")
public class UsersController {

    UsersService usersService;

    @GetMapping()
    public ResponseEntity<List<Users>> getUsers() {
        return ResponseEntity.ok(usersService.findAll());
    }

    @PostMapping()
    public ResponseEntity<Users> addUsers(@Valid @RequestBody UsersDto users) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.save(users));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Users> updateUsers(@Valid @RequestBody UsersDto users, @PathVariable Long userId) {
        return ResponseEntity.ok(usersService.update(users, userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUsers (@PathVariable Long userId) {
        usersService.delete(userId);
        return ResponseEntity.ok(String.format("User %s deleted", userId));
    }
}
