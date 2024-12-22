package com.agritrading.AgritradingApplication.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
//@AllArgsConstructor
//@NoArgsConstructor
public class ProductDTO {

    private int prod_id;
    private String prod_Name;
    private String prod_Description;
    private String category;
    private String prod_Img;
    private int prod_Stock;
    private int prod_Quantity;
    private int prod_Price;
    private Date listing_Date;

    private FarmerDTO farmerDTO;
}
