package com.example.movieapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login"; // перенаправляет на login.html
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

