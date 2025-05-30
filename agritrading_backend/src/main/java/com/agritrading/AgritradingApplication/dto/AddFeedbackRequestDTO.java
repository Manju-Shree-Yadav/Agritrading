package com.agritrading.AgritradingApplication.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddFeedbackRequestDTO {
    private int productId;
    private int customerId;
    private String customerPhone;
    private int rating;
    private String description;
}
