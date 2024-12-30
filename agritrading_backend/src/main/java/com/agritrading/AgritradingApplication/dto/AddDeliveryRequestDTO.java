package com.agritrading.AgritradingApplication.dto;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AddDeliveryRequestDTO {
    private int deliveryId;
    private int orderId;
    private int trackingNumber;
    private Date estimatedArrivalTime;
    private String deliveryAddress;
}
