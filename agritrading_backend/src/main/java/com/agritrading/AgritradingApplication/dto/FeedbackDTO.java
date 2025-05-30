package com.agritrading.AgritradingApplication.dto;

import com.agritrading.AgritradingApplication.model.Customers;
import com.agritrading.AgritradingApplication.model.Products;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDTO {

    private CustomerDTO customer;
    private String customerPhone;
    private ProductDTO product;
    private int rating;
    private String description;
}
