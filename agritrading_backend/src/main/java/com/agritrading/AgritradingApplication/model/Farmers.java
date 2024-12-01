package com.agritrading.AgritradingApplication.model;

import jakarta.persistence.*;

@Entity

public class Farmers{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int farmerId;
    @Column(nullable = false)
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    private String name;
    private String farmLocation;
    private String contactInfo;

    @Override
    public String toString() {
        return "Farmers{" +
                "farmerId=" + farmerId +
                ", name='" + name + '\'' +
                ", farmLocation='" + farmLocation + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", farmType='" + farmType + '\'' +
                ", certification='" + certification + '\'' +
                '}';
    }

    private String farmType;
    private String certification;
}
