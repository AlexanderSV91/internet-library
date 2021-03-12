package com.faceit.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/orderbook")
    public String orderbook() {
        return "orderbook";
    }

    @GetMapping("/book")
    public String book() {
        return "book";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/confirm/**")
    public String confirm() {
        return "successfulPage";
    }
}
