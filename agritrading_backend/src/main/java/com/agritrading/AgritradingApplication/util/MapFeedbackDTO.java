package com.agritrading.AgritradingApplication.util;

import com.agritrading.AgritradingApplication.dto.FeedbackDTO;
import com.agritrading.AgritradingApplication.model.Feedback;

import java.util.List;
import java.util.stream.Collectors;

public class MapFeedbackDTO {
    public static FeedbackDTO map(Feedback feedback){
        return  FeedbackDTO.builder()
                .customer(MapCustomerDTO.map(feedback.getCustomer()))
                .product(MapProductDTO.map(feedback.getProduct()))
                .customerPhone(feedback.getCustomerPhone())
                .description(feedback.getDescription())
                .rating(feedback.getRating())
                .build();
    }

    public static List<FeedbackDTO> map(List<Feedback> feedbacks ){
        return feedbacks.stream().map(MapFeedbackDTO::map).collect(Collectors.toList());
    }
}
