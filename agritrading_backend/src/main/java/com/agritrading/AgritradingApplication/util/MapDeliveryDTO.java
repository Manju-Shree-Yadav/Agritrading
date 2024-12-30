package com.agritrading.AgritradingApplication.util;

import com.agritrading.AgritradingApplication.dto.DeliveryDTO;
import com.agritrading.AgritradingApplication.model.Delivery;

import java.util.List;
import java.util.stream.Collectors;

public class MapDeliveryDTO {

    public static DeliveryDTO map(Delivery delivery){
        return  DeliveryDTO.builder()
                .deliveryId(delivery.getId())
                .deliveryAddress(delivery.getDeliveryAddress())
                .trackingNumber(delivery.getTrackingNumber())
                .order(MapOrderDTO.map(delivery.getOrder()))
                .estimatedArrivalTime(delivery.getEstimatedArrivalTime())
                .build();
    }

    public static List<DeliveryDTO> map(List<Delivery> deliveryList ){
        return deliveryList.stream().map(MapDeliveryDTO::map).collect(Collectors.toList());
    }
}
