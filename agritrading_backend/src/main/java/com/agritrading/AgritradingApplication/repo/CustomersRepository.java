package com.agritrading.AgritradingApplication.repo;

import com.agritrading.AgritradingApplication.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Integer> {
}