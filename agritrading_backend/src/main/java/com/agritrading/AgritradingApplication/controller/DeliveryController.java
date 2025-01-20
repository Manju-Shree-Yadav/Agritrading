package com.agritrading.AgritradingApplication.controller;

import com.agritrading.AgritradingApplication.dto.AddDeliveryRequestDTO;
import com.agritrading.AgritradingApplication.dto.Response;
import com.agritrading.AgritradingApplication.model.Delivery;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import com.agritrading.AgritradingApplication.service.DeliveryService;
import com.agritrading.AgritradingApplication.util.MapDeliveryDTO;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class DeliveryController{
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/delivery")
    public ResponseEntity<Response> addDelivery(@RequestBody AddDeliveryRequestDTO delivery) {
        Delivery deliveryres =  deliveryService.addDelivery(delivery);
        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Delivery added")
                .delivery(MapDeliveryDTO.map(deliveryres))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/delivery")
    public ResponseEntity<Response> getDeliveryById(@RequestParam("id")Optional<Integer> id) {
        Delivery deliveryres =  deliveryService.getDelivery(id.get());

        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Delivery added")
                .delivery(MapDeliveryDTO.map(deliveryres))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/alldeliveries")
    public ResponseEntity<Response> getAllDelivery(Authentication authentication) {
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username);

        List<Delivery> deliveries =  deliveryService.getAllDeliveries(user.getFarmerId(), user.getCustomerId());

        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Delivery added")
                .deliveryList(MapDeliveryDTO.map(deliveries))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/delivery-byorderid")
    public ResponseEntity<Response> getDeliveryByOrderId(@RequestParam("id")Optional<Integer> id) {
        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Delivery Fetched")
                .delivery(MapDeliveryDTO.map(deliveryService.getDeliveryByOrderId(id.get())))
                .build();


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }







}
