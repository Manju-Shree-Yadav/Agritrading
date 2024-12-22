package com.agritrading.AgritradingApplication.repo;

import com.agritrading.AgritradingApplication.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    @Query("SELECT d FROM Delivery d JOIN d.order o JOIN o.product p WHERE p.farmer.farmerId =:farmerId")
    List<Delivery> findForFarmer(@Param("farmerId") int farmerId);


    @Query("SELECT d FROM Delivery d JOIN d.order o WHERE o.customer.customerId =:customerId")
    List<Delivery> findForCustomer(@Param("customerId") int customerId);


}
