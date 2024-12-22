package com.agritrading.AgritradingApplication.util;

import com.agritrading.AgritradingApplication.dto.AddProductResponseDTO;
import com.agritrading.AgritradingApplication.dto.ProductDTO;
import com.agritrading.AgritradingApplication.model.Products;

import java.util.List;
import java.util.stream.Collectors;

public class MapProductDTO {

    public static ProductDTO map(Products products) {
        return ProductDTO.builder()
                .prod_id(products.getProd_id())
                .prod_Name(products.getProd_Name())
                .prod_Description(products.getProd_Description())
                .category(products.getCategory())
                .prod_Img(products.getProd_Img())
                .prod_Stock(products.getProd_Stock())
                .prod_Quantity(products.getProd_Quantity())
                .prod_Price(products.getProd_Price())
                .listing_Date(products.getListing_Date())
                .farmerDTO(MapFarmerDTO.map(products.getFarmer()))
                .build();
    }

    public static List<ProductDTO> map(List<Products> products) {
        return products.stream()
                .map(MapProductDTO::map) // Use class reference for static methods
                .collect(Collectors.toList());
    }
}
