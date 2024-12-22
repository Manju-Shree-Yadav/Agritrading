package com.agritrading.AgritradingApplication.repo;

import com.agritrading.AgritradingApplication.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Integer> {
    @Query("SELECT pr FROM Payments pr WHERE pr.order.order_Id = :orderId ")
    Payments findByOrderId(@Param("orderId") int orderId);

}
