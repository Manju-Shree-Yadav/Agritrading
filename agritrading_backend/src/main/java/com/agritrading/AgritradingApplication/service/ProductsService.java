package com.agritrading.AgritradingApplication.service;

import com.agritrading.AgritradingApplication.model.Products;
import com.agritrading.AgritradingApplication.repo.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public  Products addProduct(Products product) {
        return productsRepository.save(product);
    }

    public List<Products> getAllProducts(int farmerId) {
        return productsRepository.findAllByFarmerId(farmerId);
    }

    public Products getProductById(Integer id) throws Exception {
        Optional<Products> productOptional = productsRepository.findById(id);

        // Check if the product exists and return it, otherwise return null or throw an exception
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            // Handle the case where the product is not found
            throw new Exception("Product not foundww with id: " + id);
        }
    }

    public List<Products> getProductsByCategory(String category, Integer farmerId) {
        return productsRepository.findProductsByCategory(farmerId, category);
    }

}
