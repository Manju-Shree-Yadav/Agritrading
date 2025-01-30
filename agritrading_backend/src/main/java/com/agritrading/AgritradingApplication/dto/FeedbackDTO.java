package com.agritrading.AgritradingApplication.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDTO {

    private int customerId;
    private String customerPhone;
    private int productId;
    private int rating;
    private String description;
}
