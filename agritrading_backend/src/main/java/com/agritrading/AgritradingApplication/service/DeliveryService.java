package com.agritrading.AgritradingApplication.service;


import com.agritrading.AgritradingApplication.model.Delivery;
import com.agritrading.AgritradingApplication.repo.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;
    public Delivery addDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
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
}
