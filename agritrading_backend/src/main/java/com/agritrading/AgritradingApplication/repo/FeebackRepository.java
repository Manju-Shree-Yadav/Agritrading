package com.agritrading.AgritradingApplication.repo;

import com.agritrading.AgritradingApplication.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeebackRepository extends JpaRepository<Feedback,Integer> {


    List<Feedback> getByCustomerId(int customerId);
    Feedback getByProductId(int productId);
}
