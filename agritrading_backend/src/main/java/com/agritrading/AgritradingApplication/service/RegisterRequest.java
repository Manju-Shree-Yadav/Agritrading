package com.agritrading.AgritradingApplication.service;


public class RegisterRequest {

    private String username;
    private String password;
    private String name;
    private String role;
    private String farmLocation;
    private String contactInfo;
    private String customerAdditionalInfo; // Add any other customer-specific info here
    private String farmType;
    private String certification;

    public String getPreferredPaymentMethod() {
        return preferredPaymentMethod;
    }

    public void setPreferredPaymentMethod(String preferredPaymentMethod) {
        this.preferredPaymentMethod = preferredPaymentMethod;
    }

    private String preferredPaymentMethod;
    public String getFarmType() {
        return farmType;
    }

    public void setFarmType(String farmType) {
        this.farmType = farmType;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    // Getters and Setters
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFarmLocation() {
        return farmLocation;
    }

    public void setFarmLocation(String farmLocation) {
        this.farmLocation = farmLocation;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getCustomerAdditionalInfo() {
        return customerAdditionalInfo;
    }

    public void setCustomerAdditionalInfo(String customerAdditionalInfo) {
        this.customerAdditionalInfo = customerAdditionalInfo;
    }


}
