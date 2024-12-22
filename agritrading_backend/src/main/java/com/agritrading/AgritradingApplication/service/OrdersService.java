package com.agritrading.AgritradingApplication.service;

import com.agritrading.AgritradingApplication.dto.AddOrderDTO;
import com.agritrading.AgritradingApplication.dto.AddOrderResponseDTO;
import com.agritrading.AgritradingApplication.model.Customers;
import com.agritrading.AgritradingApplication.model.Orders;
import com.agritrading.AgritradingApplication.model.Products;
import com.agritrading.AgritradingApplication.repo.CustomersRepository;
import com.agritrading.AgritradingApplication.repo.OrdersRepository;
import com.agritrading.AgritradingApplication.repo.ProductsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private ProductsRepository productsRepository;
    public AddOrderResponseDTO mapToOrderResponseDTO(Orders order) {
        return AddOrderResponseDTO.builder()
                .orderId(order.getOrder_Id())
                .customerName(order.getCustomer().getName())
                .productName(order.getProduct().getProd_Name())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotal_Price())
                .orderStatus(order.getOrder_status())
                .build();
    }

    public Orders addOrder(Orders order) {
        return ordersRepository.save(order);
    }

    public List<AddOrderResponseDTO> getAllOrders(int customerId) {
        List<Orders> orders = ordersRepository.findByCustomerId(customerId);
        return orders.stream()
                .map(this::mapToOrderResponseDTO)
                .collect(Collectors.toList());

    }

    public Orders getOrder(int orderId) throws Exception {
        if(ordersRepository.existsById(orderId)) {
            return ordersRepository.findById(orderId).get();
        }
        return null;
    }


    public void deleteOrders(int orderId) {
        ordersRepository.deleteById(orderId);
    }

    public List<AddOrderResponseDTO> getAllOrdersForFarmer(int farmerId) {
        List<Orders> orders =  ordersRepository.getOrdersByFarmerId(farmerId);
        return orders.stream()
                .map(this::mapToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Orders createOrder(AddOrderDTO orderRequest, Customers customer) {
        // Create a new order entity
        Orders orders = new Orders();
        orders.setCustomer(customer);

        // Fetch the product using prod_id from the request
        int productId = orderRequest.getProductId();
//        System.out.println("Product ID: " + productId);
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        orders.setProduct(product);

        // Handle stock
        int productStock = product.getProd_Stock();
        if (orderRequest.getQuantity() > productStock) {
            throw new RuntimeException("Insufficient stock");
        }
        product.setProd_Stock(productStock - orderRequest.getQuantity());
        productsRepository.save(product);

        // Calculate total price
        orders.setQuantity(orderRequest.getQuantity());
        orders.setTotal_Price(product.getProd_Price() * orderRequest.getQuantity());

        // Set order status
        orders.setOrder_status("PENDING");

        // Save and return the order
        return ordersRepository.save(orders);
    }

    public Orders updateOrder(Orders orders, Integer orderId , Integer customerId, Integer farmerId) {
        if(customerId != null) {
            if(ordersRepository.findById(orderId).isPresent()) {
                if(ordersRepository.findById(orderId).get().getCustomer().getCustomerId()==customerId) {
                    ordersRepository.deleteById(orderId);
                    orders.setCustomer(customersRepository.findById(customerId).get());
                    ordersRepository.save(orders);
                    return orders;
                }
            }
        }

        if(farmerId != null) {
            if(ordersRepository.findById(orderId).isPresent()) {
                if(ordersRepository.findById(orderId).get().getProduct().getFarmer().getFarmerId()==farmerId) {
                    customerId = ordersRepository.findById(orderId).get().getCustomer().getCustomerId();
                    ordersRepository.deleteById(orderId);
                    orders.setCustomer(customersRepository.findById(customerId).get());
                    ordersRepository.save(orders);
                    return orders;
                }
            }
        }

        throw new RuntimeException("Order not found");

    }
}
