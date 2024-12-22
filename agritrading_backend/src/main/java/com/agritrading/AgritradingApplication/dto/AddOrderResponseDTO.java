package com.agritrading.AgritradingApplication.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddOrderResponseDTO {
    private int orderId;
    private String customerName;
    private String productName;
    private int quantity;
    private int totalPrice;
    private String orderStatus;
}
