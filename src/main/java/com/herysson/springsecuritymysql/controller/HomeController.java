package com.herysson.springsecuritymysql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @GetMapping("/index")
    public String index() {
        return "index"; // aponta para um arquivo `index.html` no `templates`
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/adminpanel")
    public String adminPanel() {
        return "adminpanel";
    }
}
