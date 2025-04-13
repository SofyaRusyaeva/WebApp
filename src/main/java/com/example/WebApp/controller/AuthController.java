package com.example.WebApp.controller;

import com.example.WebApp.dto.AuthDto;
import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {

    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsersDto userDto) {
        return ResponseEntity.ok(authService.save(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        AuthDto authDto = new AuthDto(username, password);
        String token = authService.authenticate(authDto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
}
