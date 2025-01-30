package com.agritrading.AgritradingApplication.service;

import com.agritrading.AgritradingApplication.dto.AddFeedbackRequestDTO;
import com.agritrading.AgritradingApplication.model.Feedback;
import com.agritrading.AgritradingApplication.repo.FeebackRepository;
import com.agritrading.AgritradingApplication.repo.OrdersRepository;
import com.agritrading.AgritradingApplication.repo.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeebackRepository feebackRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private OrdersRepository ordersRepository;

    public Feedback addFeedback(AddFeedbackRequestDTO feedback) {

        Feedback feedback1 = new Feedback();
        feedback1.setCustomerId(feedback.getCustomerId());
        feedback1.setCustomerPhone(feedback.getCustomerPhone());
        feedback1.setProductId(feedback.getProductId());
        feedback1.setRating(feedback.getRating());
        feedback1.setDescription(feedback.getDescription());
        return feebackRepository.save(feedback1);
    }

//    public Feedback getAllFeedback(int id) {
//        return deliveryRepository.findById(id).orElse(null);
//    }

//    public List<Feedback> getAllFeedback(Integer ProductId) {
//        if(ProductId != null) {
//                return FeebackRepository.findForProductId(ProductId);
//
//        }
//        return null;
//    }

    public List<Feedback> getByCustomerId(int customerId) {
        return feebackRepository.getByCustomerId(customerId);
    }

}
