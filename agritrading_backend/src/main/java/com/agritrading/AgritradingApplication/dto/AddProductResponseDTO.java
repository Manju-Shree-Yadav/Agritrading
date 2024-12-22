package com.agritrading.AgritradingApplication.dto;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AddProductResponseDTO {
    private int prod_id;
    private String prod_Name;
    private String prod_Description;
    private String category;
    private String prod_Img;
    private int prod_Stock;
    private int prod_Quantity;
    private int prod_Price;
    private Date listing_Date;
}
