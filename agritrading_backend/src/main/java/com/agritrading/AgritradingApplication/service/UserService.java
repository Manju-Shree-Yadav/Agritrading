package com.agritrading.AgritradingApplication.service;

import com.agritrading.AgritradingApplication.model.Customers;
import com.agritrading.AgritradingApplication.model.Farmers;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.CustomersRepository;
import com.agritrading.AgritradingApplication.repo.FarmersRepository;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FarmersRepository farmersRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Registration method accepting RegisterRequest
    public Users register(RegisterRequest registerRequest) {
        // Create the user
        Users user = new Users();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());
        user = userRepo.save(user);

        // Handle role-specific table mapping
        switch (user.getRole().toUpperCase()) {
            case "FARMER":
                Farmers farmer = new Farmers();
                farmer.setUserId(user.getId()); // Link with user_id
                farmer.setName(registerRequest.getName());
                farmer.setFarmLocation(registerRequest.getFarmLocation());
                farmer.setContactInfo(registerRequest.getContactInfo());
                farmer.setFarmType(registerRequest.getFarmType());
                farmer.setCertification(registerRequest.getCertification());
                Farmers savedFarmer = farmersRepository.save(farmer); // Save farmer
                user.setFarmerId(savedFarmer.getFarmerId());
                userRepo.save(user);
                break;
            case "CUSTOMER":
                Customers customer = new Customers();
                customer.setUserId(user.getId()); // Link with user_id
                customer.setName(registerRequest.getName());
                customer.setAddress(registerRequest.getFarmLocation());
                customer.setContactInfo(registerRequest.getContactInfo());
                customer.setPreferredPaymentMethod(registerRequest.getPreferredPaymentMethod());
                Customers savedCustomer = customersRepository.save(customer); // Save customer
                user.setCustomerId(savedCustomer.getCustomerId());
                userRepo.save(user);
                break;
            case "ADMIN":
                // No additional action needed
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + user.getRole());
        }

        return user;
    }



    // Authentication and token generation
    public String verify(Users user) {
        // Authenticate user credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // Generate JWT token
            return jwtService.generateToken(user.getUsername());
        }

        throw new IllegalArgumentException("Authentication failed for user: " + user.getUsername());
    }
}
