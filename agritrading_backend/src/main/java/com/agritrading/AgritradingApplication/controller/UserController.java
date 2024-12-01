package com.agritrading.AgritradingApplication.controller;


import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.service.RegisterRequest;
import com.agritrading.AgritradingApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public  Users register(@RequestBody RegisterRequest user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public  String login(@RequestBody Users user) {
        return userService.verify(user);
    }
}
