package com.agritrading.AgritradingApplication.util;

import com.agritrading.AgritradingApplication.dto.CustomerDTO;
import com.agritrading.AgritradingApplication.dto.PaymentDTO;
import com.agritrading.AgritradingApplication.dto.ProductDTO;
import com.agritrading.AgritradingApplication.model.Customers;
import com.agritrading.AgritradingApplication.model.Payments;
import com.agritrading.AgritradingApplication.model.Products;

import java.util.List;
import java.util.stream.Collectors;

public class MapCustomerDTO {
    public static CustomerDTO map(Customers customer){
        return CustomerDTO.builder()
                .preferredPaymentMethod(customer.getPreferredPaymentMethod())
                .address(customer.getAddress())
                .name(customer.getName())
                .contactInfo(customer.getContactInfo())
                .build();
    }

    public static List<ProductDTO> map(List<Products> products){

        return products.stream().map(MapProductDTO::map).collect(Collectors.toList());

    }
}
