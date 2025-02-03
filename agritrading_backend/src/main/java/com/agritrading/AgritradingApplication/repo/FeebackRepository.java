package com.agritrading.AgritradingApplication.repo;

import com.agritrading.AgritradingApplication.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeebackRepository extends JpaRepository<Feedback,Integer> {

    @Query("select f from Feedback f where f.customer.customerId=:customerId")
    List<Feedback> getByCustomerId(int customerId);

    @Query("select f from Feedback f where f.product.prod_id=:productId")
    List<Feedback> getByProductId(int productId);
}
