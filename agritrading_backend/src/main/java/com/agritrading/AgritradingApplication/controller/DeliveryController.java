package com.agritrading.AgritradingApplication.controller;

import com.agritrading.AgritradingApplication.model.Delivery;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import com.agritrading.AgritradingApplication.service.DeliveryService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DeliveryController{
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/delivery")
    public Delivery addDelivery(@RequestBody Delivery delivery) {
        return deliveryService.addDelivery(delivery);
    }

    @GetMapping("/delivery/{id}")
    public Delivery getDeliveryById(@PathVariable int  id) {
        return deliveryService.getDelivery(id);
    }

    @GetMapping("/delivery")
    public List<Delivery> getAllDelivery(Authentication authentication) {
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username);

        return deliveryService.getAllDeliveries(user.getFarmerId(), user.getCustomerId());
    }



}
