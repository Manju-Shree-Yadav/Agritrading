package com.agritrading.AgritradingApplication.repo;

import com.agritrading.AgritradingApplication.model.Farmers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmersRepository extends JpaRepository<Farmers, Integer> {
}
