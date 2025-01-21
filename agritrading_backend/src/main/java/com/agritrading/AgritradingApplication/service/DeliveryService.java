package com.agritrading.AgritradingApplication.service;


import com.agritrading.AgritradingApplication.dto.AddDeliveryRequestDTO;
import com.agritrading.AgritradingApplication.model.Delivery;
import com.agritrading.AgritradingApplication.repo.DeliveryRepository;
import com.agritrading.AgritradingApplication.repo.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    public Delivery addDelivery(AddDeliveryRequestDTO delivery) {

        Delivery delivery1 = new Delivery();
        delivery1.setDeliveryAddress(delivery.getDeliveryAddress());
        delivery1.setEstimatedArrivalTime(delivery.getEstimatedArrivalTime());
        delivery1.setTrackingNumber(delivery.getTrackingNumber());
        delivery1.setOrder(ordersRepository.findById(delivery.getOrderId()).get());
        delivery1.setDeliveryStatus(delivery.getDeliveryStatus());
        return deliveryRepository.save(delivery1);
    }

    public Delivery getDelivery(int id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    public List<Delivery> getAllDeliveries(Integer farmerId, Integer customerId) {
        if(farmerId != null || customerId != null) {
            if(farmerId != null) {
                return deliveryRepository.findForFarmer(farmerId);
            }
            if(customerId != null) {
                return deliveryRepository.findForCustomer(customerId);
            }
        }
        return null;
    }

    public Delivery getDeliveryByOrderId(Integer orderId) {
        return deliveryRepository.findByOrderId(orderId);
    }
}
