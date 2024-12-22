package com.agritrading.AgritradingApplication.util;

import com.agritrading.AgritradingApplication.dto.OrderDTO;
import com.agritrading.AgritradingApplication.model.Orders;


public class MapOrderDTO {
    public static OrderDTO map(Orders order){
        return OrderDTO.builder()
                .orderId(order.getOrder_Id())
                .order_date(order.getOrder_date())
                .order_status(order.getOrder_status())
                .quantity(order.getQuantity())
                .total_Price(order.getTotal_Price())
                .product(MapProductDTO.map(order.getProduct()))
                .build();
    }
}
