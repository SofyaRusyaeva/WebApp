package com.example.WebApp.controller;

import com.example.WebApp.config.JwtProvider;
import com.example.WebApp.dto.PasswordDto;
import com.example.WebApp.dto.UserUpdateDto;
import com.example.WebApp.dto.UsersDto;
import com.example.WebApp.model.Users;
import com.example.WebApp.service.UsersService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
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
    public String getMe(Model model) {
        Long userId = jwtProvider.getCurrentUserId();
        model.addAttribute("user", usersService.findById(userId));
        return "profile";
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me/edit")
    @ResponseBody
    public ResponseEntity<Users> updateUsers(@RequestBody UserUpdateDto users) {
        usersService.update(users);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/me/edit/password")
    @ResponseBody
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto) {
        usersService.updatePassword(passwordDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/me")
    @ResponseBody
    public ResponseEntity<?> deleteUsers (HttpServletResponse response) {
        Long userId = jwtProvider.getCurrentUserId();
        usersService.delete(userId);

        Cookie cookie = new Cookie("access_token", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.noContent().build();
    }
}
