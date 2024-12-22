package com.agritrading.AgritradingApplication.service;

import com.agritrading.AgritradingApplication.dto.AddPaymentDTO;
import com.agritrading.AgritradingApplication.dto.PaymentDTO;
import com.agritrading.AgritradingApplication.model.Orders;
import com.agritrading.AgritradingApplication.model.Payments;
import com.agritrading.AgritradingApplication.util.MapPaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.agritrading.AgritradingApplication.repo.PaymentsRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class PaymentService {


    private final PaymentsRepository paymentsRepository;

    public PaymentService(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    public PaymentDTO createPayments(AddPaymentDTO payments , Orders order) {
        Payments newPayments = new Payments();
        newPayments.setAmount(payments.getAmount());
        newPayments.setPaymentType(payments.getPaymentType());
        newPayments.setPaymentStatus(payments.getPaymentStatus());
        newPayments.setOrder(order);
//        newPayments.setPaymentDate(Date.UTC());
        Payments payment =  paymentsRepository.save(newPayments);
        return MapPaymentDTO.map(payment);
    }

    public Payments getPaymentById(Optional<Integer> id) {
        if(id.isPresent()) {
            return paymentsRepository.findById(id.get()).orElse(null);
        }
        return null;
    }


    public void deletePaymentbyId(Optional<Integer> id) {
        if(id.isPresent()) {
            paymentsRepository.deleteById(id.get());
        }
    }

    public Payments getPaymentByOrderId(Optional<Integer> orderdId) {
        if(orderdId.isPresent()) {
            return paymentsRepository.findByOrderId(orderdId.get());
        }
        return null;
    }

    public Payments updatePayment(AddPaymentDTO payments, int id , Orders order) {

        paymentsRepository.deleteById(id);
        Payments newPayments = new Payments();
        newPayments.setAmount(payments.getAmount());
        newPayments.setPaymentType(payments.getPaymentType());
        newPayments.setPaymentStatus(payments.getPaymentStatus());
        newPayments.setOrder(order);
        return paymentsRepository.save(newPayments);
    }
}
