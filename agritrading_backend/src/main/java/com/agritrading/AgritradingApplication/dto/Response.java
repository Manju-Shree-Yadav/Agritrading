package com.agritrading.AgritradingApplication.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int status;
    private String message;
    private final LocalDateTime timestamp= LocalDateTime.now();


    private CustomerDTO customer;
    private FarmerDTO farmer;

    private OrderDTO order;
    private List<OrderDTO> orderList;

    private AddOrderResponseDTO addOrderResponse;
    private List<AddOrderResponseDTO> addOrderResposeList;

    private ProductDTO product;
    private List<ProductDTO> productList;

    private PaymentDTO payment;
    private List<PaymentDTO> paymentList;
}
