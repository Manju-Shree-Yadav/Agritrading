package com.agritrading.AgritradingApplication.dto;

import com.agritrading.AgritradingApplication.model.Orders;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DeliveryDTO {
    private int deliveryId;
    private OrderDTO order;
    private int trackingNumber;
    private Date estimatedArrivalTime;
    private String deliveryAddress;
    private String deliveryStatus;
}
