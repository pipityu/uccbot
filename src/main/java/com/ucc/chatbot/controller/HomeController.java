package com.ucc.chatbot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@CrossOrigin
@RestController
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "userhome";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/admin/home")
    public String adminhome() {
        return "adminhome";
    }

}
