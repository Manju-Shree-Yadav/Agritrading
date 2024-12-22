package com.agritrading.AgritradingApplication.repo;


import com.agritrading.AgritradingApplication.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Query("select o from Orders  o where o.customer.customerId=:customerid ")
    public List<Orders> findByCustomerId(@Param("customerid") Integer customer_id);

    @Query("select o from Orders o join o.product p where p.farmer.farmerId=:farmer_id ")
    List<Orders> getOrdersByFarmerId(int farmer_id);
}
