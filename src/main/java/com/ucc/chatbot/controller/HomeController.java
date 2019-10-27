package com.ucc.chatbot.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



@Controller
public class HomeController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "userhome";
    }

    @GetMapping(path = "/info", produces = "application/json")
    public String getTodosJson() {
        String theUrl = "https://jsonplaceholder.typicode.com/todos";
        ResponseEntity<String> response = restTemplate.exchange(theUrl, HttpMethod.GET, null, String.class);

        return response.getBody();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin/home")
    public String adminhome() {
        return "adminhome";
    }

}
