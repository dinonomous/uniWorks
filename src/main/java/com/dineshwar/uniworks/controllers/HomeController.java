package com.dineshwar.uniworks.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "Welcome to the University of Works! This is a simple Spring Boot application.";
    }

    @GetMapping("/about")
    public String about(){
        return "This is the about page.";
    }

    @GetMapping("/admin/home")
    public String getAdminHome(){
        return "this is the admin home page.";
    }

    @GetMapping("/client/home")
    public String clientHome(){
        return "welcome to client of Works";
    }
}
