package com.example.WebApp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/api/auth/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/api/auth/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/api/shop/home")
    public String homePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        return "home";
    }
}
