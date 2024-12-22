package com.agritrading.AgritradingApplication.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private int orderId;
    private int quantity;
    private int total_Price;
    private Date order_date;
    private String order_status;
//    private CustomerDTO customer;
    private ProductDTO product;

}
