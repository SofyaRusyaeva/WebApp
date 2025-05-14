package com.example.WebApp.controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = false;
        if (authentication != null && authentication.isAuthenticated()) {
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("username", authentication != null ? authentication.getName() : "");
    }
}