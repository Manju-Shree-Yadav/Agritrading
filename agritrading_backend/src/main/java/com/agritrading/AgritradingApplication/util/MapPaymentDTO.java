package com.agritrading.AgritradingApplication.util;

import com.agritrading.AgritradingApplication.dto.PaymentDTO;
import com.agritrading.AgritradingApplication.dto.ProductDTO;
import com.agritrading.AgritradingApplication.model.Payments;
import com.agritrading.AgritradingApplication.model.Products;

import java.util.List;
import java.util.stream.Collectors;

public class MapPaymentDTO {

    public static PaymentDTO map(Payments payment){
        return PaymentDTO.builder()
                .paymentId(payment.getPaymentId())
                .amount(payment.getAmount())
                .paymentType(payment.getPaymentType())
                .paymentDate(payment.getPaymentDate())
                .paymentStatus(payment.getPaymentStatus())
                .order(MapOrderDTO.map(payment.getOrder()))
                .build();
    }

    public static List<ProductDTO> map(List<Products> products){

        return products.stream().map(MapProductDTO::map).collect(Collectors.toList());

    }
}
