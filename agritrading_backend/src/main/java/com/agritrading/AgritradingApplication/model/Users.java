package com.agritrading.AgritradingApplication.model;

import jakarta.persistence.*;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String role; // 'FARMER', 'CUSTOMER', 'ADMIN'
    @Column(nullable = true)
    private Integer farmerId;

    @Column(nullable = true)
    private Integer customerId;

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", farmerId=" + farmerId +
                ", customerId=" + customerId +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    public Farmers getFarmer() {
//        return farmer;
//    }
//
//    public void setFarmer(Farmers farmer) {
//        this.farmer = farmer;
//    }
//
//    public Customers getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customers customer) {
//        this.customer = customer;
//    }

//    @OneToOne
//    @JoinColumn(name = "farmer_id", referencedColumnName = "farmerId", nullable = true)
//    private Farmers farmer;
//
//    @OneToOne
//    @JoinColumn(name = "customer_id", referencedColumnName = "customerId", nullable = true)
//    private Customers customer;
}
