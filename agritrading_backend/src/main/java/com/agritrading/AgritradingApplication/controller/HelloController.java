package com.agritrading.AgritradingApplication.controller;

import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController  {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String greet(Authentication authentication) {
        String username = authentication.getName();

        // Fetch user details from the database
        Users user = userRepo.findByUsername(username);

        // Return a personalized greeting
        return "Hello, " + user.getUsername() + "!";
    }


}
