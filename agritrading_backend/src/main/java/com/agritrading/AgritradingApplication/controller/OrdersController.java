package com.agritrading.AgritradingApplication.controller;

import com.agritrading.AgritradingApplication.model.Orders;
import com.agritrading.AgritradingApplication.model.Products;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.ProductsRepository;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import com.agritrading.AgritradingApplication.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class OrdersController {

    public int customerId(Authentication authentication) {
        String username = authentication.getName();

        Users user = userRepo.findByUsername(username);

        return user.getCustomerId();
    }

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductsRepository productsRepository;

    @PostMapping("/orders")
    public Orders createOrder(@RequestBody Orders orders, Authentication authentication) throws Exception {
        orders.setCustomer_Id(customerId(authentication));
        int productId = orders.getProduct_Id();

        //handling Stock
        Products products = productsRepository.findbyId(productId);
        int product_stock = products.getProd_Stock();
        if(orders.getQuantity()< product_stock) products.setProd_Stock(product_stock-orders.getQuantity());
        else throw new Exception();
        productsRepository.save(products);

        //Calculating Pricing
        orders.setTotal_Price(products.getProd_Price()*orders.getQuantity());
        orders.setOrder_status("PENDING");
        return ordersService.addOrder(orders);
    }

    @GetMapping("/orders/by-id")
    public Orders getOrdersById(@RequestParam("id")Optional<Integer> orderId, Authentication authentication) throws Exception {
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username);
        if(orderId.isPresent()) {
            return ordersService.getOrder(orderId.get());
        }
        else {
            throw new Exception("No orderId found");
        }

    }
    @GetMapping("/orders")
    public List<Orders> getAllOrders(Authentication authentication) {
        return ordersService.getAllOrders(customerId(authentication));
    }

}
