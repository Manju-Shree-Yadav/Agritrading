package com.agritrading.AgritradingApplication.model;

import jakarta.persistence.*;
import org.springframework.context.annotation.Primary;

import java.util.Date;
import java.util.List;

@Entity
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prod_id;


    private String prod_Name;
    private String prod_Description;
    private String category;
    private String prod_Img;
    private int prod_Stock;
    private int prod_Quantity;
    private int prod_Price;
    private Date listing_Date;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmerId")
    private Farmers farmer;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Orders> ordersList;

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_Name() {
        return prod_Name;
    }

    public void setProd_Name(String prod_Name) {
        this.prod_Name = prod_Name;
    }

    public String getProd_Description() {
        return prod_Description;
    }

    public void setProd_Description(String prod_Description) {
        this.prod_Description = prod_Description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProd_Img() {
        return prod_Img;
    }

    public void setProd_Img(String prod_Img) {
        this.prod_Img = prod_Img;
    }

    public int getProd_Stock() {
        return prod_Stock;
    }

    public void setProd_Stock(int prod_Stock) {
        this.prod_Stock = prod_Stock;
    }

    public int getProd_Quantity() {
        return prod_Quantity;
    }

    public void setProd_Quantity(int prod_Quantity) {
        this.prod_Quantity = prod_Quantity;
    }

    public int getProd_Price() {
        return prod_Price;
    }

    public void setProd_Price(int prod_Price) {
        this.prod_Price = prod_Price;
    }

    public Date getListing_Date() {
        return listing_Date;
    }

    @Override
    public String toString() {
        return "Products{" +
                "prod_id=" + prod_id +
                ", prod_Name='" + prod_Name + '\'' +
                ", prod_Description='" + prod_Description + '\'' +
                ", category='" + category + '\'' +
                ", prod_Img='" + prod_Img + '\'' +
                ", prod_Stock=" + prod_Stock +
                ", prod_Quantity=" + prod_Quantity +
                ", prod_Price=" + prod_Price +
                ", listing_Date=" + listing_Date +
                ", farmer=" + farmer +
                '}';
    }

    public void setListing_Date(Date listing_Date) {
        this.listing_Date = listing_Date;
    }

    public Farmers getFarmer() {
        return farmer;
    }

    public void setFarmer(Farmers farmer) {
        this.farmer = farmer;
    }




}
