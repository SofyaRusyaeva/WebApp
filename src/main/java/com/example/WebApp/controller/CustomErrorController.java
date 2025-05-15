package com.example.WebApp.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/api/shop/error403")
    public String forbidden(Model model) {
        model.addAttribute("error", "У вас нет доступа к этой странице.");
        return "error403";
    }

    @GetMapping("/api/shop/error404")
    public String notFound(Model model) {
        model.addAttribute("error", "Страница не найдена.");
        return "error404";
    }
}
