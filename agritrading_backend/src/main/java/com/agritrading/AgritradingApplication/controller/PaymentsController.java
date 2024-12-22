package com.agritrading.AgritradingApplication.controller;

import com.agritrading.AgritradingApplication.dto.AddPaymentDTO;
import com.agritrading.AgritradingApplication.dto.PaymentDTO;
import com.agritrading.AgritradingApplication.dto.ProductDTO;
import com.agritrading.AgritradingApplication.dto.Response;
import com.agritrading.AgritradingApplication.model.Orders;
import com.agritrading.AgritradingApplication.model.Payments;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import com.agritrading.AgritradingApplication.service.OrdersService;
import com.agritrading.AgritradingApplication.service.PaymentService;
import com.agritrading.AgritradingApplication.service.ProductsService;
import com.agritrading.AgritradingApplication.util.MapPaymentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class PaymentsController {

    private final UserRepo userRepo;
    private final ProductsService productsService;
    private final PaymentService paymentService;
    private final OrdersService ordersService;

    public PaymentsController(UserRepo userRepo, ProductsService productsService, PaymentService paymentService, OrdersService ordersService) {
        this.userRepo = userRepo;
        this.productsService = productsService;
        this.paymentService = paymentService;
        this.ordersService = ordersService;
    }

    @PostMapping("/payments")
    public ResponseEntity<Response> createPayment(@RequestBody AddPaymentDTO payments) throws Exception {
        Integer orderId = payments.getOrderId();
        Orders order = ordersService.getOrder(orderId);
//        System.out.println(orderId);
//        System.out.println(order);
        PaymentDTO paymentDTO =  paymentService.createPayments(payments , order);

        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Payment created successfully")
                .payment(paymentDTO)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/payments/get-byId")
    public ResponseEntity<Response> getPaymentsbyId(@RequestParam("id") Optional<Integer> id, Authentication authentication) {
        Payments payment =  paymentService.getPaymentById(id);

        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Payment found successfully")
                .payment(MapPaymentDTO.map(payment))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/payments")
    public ResponseEntity<Response> updatePayment(@RequestParam("id") Optional<Integer>  id, @RequestBody AddPaymentDTO payments) throws Exception {

        Integer orderId = payments.getOrderId();
        Orders order = ordersService.getOrder(orderId);
        if(id.isPresent())
        {Payments payment =  paymentService.updatePayment(payments , id.get() , order);
            Response response = Response.builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Payment updated successfully")
                    .payment(MapPaymentDTO.map(payment))
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

       throw new Exception("ID not found");
    }

    @GetMapping("/payments/get-byOrderId")
    public ResponseEntity<Response> getPaymentsByOrderId(@RequestParam("orderId") Optional<Integer> orderdId, Authentication authentication) {
        Payments payment =  paymentService.getPaymentByOrderId(orderdId);

        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Order created successfully")
                .payment(MapPaymentDTO.map(payment))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/payments")
    public String deletePayment(@RequestParam("id") Optional<Integer> id, Authentication authentication) {
        paymentService.deletePaymentbyId(id);
        return "Deleted";
    }

}
