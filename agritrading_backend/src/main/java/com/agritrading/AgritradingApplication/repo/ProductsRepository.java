package com.agritrading.AgritradingApplication.repo;

import com.agritrading.AgritradingApplication.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
    @Query("SELECT p FROM Products p WHERE p.prod_id=:id")
    Products findbyId(@Param("id") Integer id);

    @Query("SELECT p FROM Products p WHERE p.farmer_id = :farmerId")
    List<Products> findAllByFarmerId(@Param("farmerId") int farmerId);

    @Query("SELECT p FROM Products p WHERE p.farmer_id =:farmerId and LOWER(p.category) = LOWER(:category)")
    List<Products> findProductsByCategory(@Param("farmerId") int farmerId, @Param("category") String category);
}
