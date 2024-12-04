package com.agritrading.AgritradingApplication.service;

import com.agritrading.AgritradingApplication.model.Orders;
import com.agritrading.AgritradingApplication.model.Products;
import com.agritrading.AgritradingApplication.repo.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    public Orders addOrder(Orders order) {
        return ordersRepository.save(order);
    }

    public List<Orders> getAllOrders(int customerId) {
        return ordersRepository.findByCustomerId(customerId);
    }

    public Orders getOrder(int orderId) throws Exception {
        if(ordersRepository.existsById(orderId)) {
            return ordersRepository.findById(orderId).get();
        }
        return null;
    }


}
