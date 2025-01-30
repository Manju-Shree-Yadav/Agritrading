package com.agritrading.AgritradingApplication.controller;

import com.agritrading.AgritradingApplication.dto.AddFeedbackRequestDTO;
import com.agritrading.AgritradingApplication.dto.Response;
import com.agritrading.AgritradingApplication.model.Feedback;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.FeebackRepository;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import com.agritrading.AgritradingApplication.service.FeedbackService;
import com.agritrading.AgritradingApplication.util.MapFeedbackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class FeedbackController {
    @Autowired
    private FeedbackService FeedbackService;
    @Autowired
    private FeebackRepository feedbackRepo;

    @PostMapping("/addfeedback")
    public ResponseEntity<Response> addFeedback(@RequestBody AddFeedbackRequestDTO feedback) {
        Feedback feedback1 =  FeedbackService.addFeedback(feedback);
        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Feedback added")
                .feedback(MapFeedbackDTO.map(feedback1))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Autowired
    private UserRepo userRepo;

    public Integer customerId(Authentication authentication) {
        String username = authentication.getName();

        Users user = userRepo.findByUsername(username);

        return user.getCustomerId();
    }

    @GetMapping("/customer/getfeedbacks")
    public ResponseEntity<Response> getAllFeedBack(Authentication authentication) {
        String username = authentication.getName();

        Users user = userRepo.findByUsername(username);
        int customerid = customerId(authentication);
        List<Feedback> feedbacks =  FeedbackService.getByCustomerId(customerid);

        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("fetchded all feeback")
                .feedbackList(MapFeedbackDTO.map(feedbacks))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/farmer/getfeedback")
    public ResponseEntity<Response> getFarmerFeeback(Authentication authentication ,@RequestParam("id")Optional<Integer> id) {
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username);

        Feedback feedback = feedbackRepo.getByProductId(id.get());
        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Feedback added")
                .feedback(MapFeedbackDTO.map(feedback))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }







}
