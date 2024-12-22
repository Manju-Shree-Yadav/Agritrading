package com.agritrading.AgritradingApplication.model;


import jakarta.persistence.*;
import org.hibernate.query.Order;

import java.util.Date;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders order;
    private int trackingNumber;
    private Date estimatedArrivalTime;
    private String deliveryAddress;

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", order=" + order +
                ", trackingNumber=" + trackingNumber +
                ", estimatedArrivalTime=" + estimatedArrivalTime +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Date getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    public void setEstimatedArrivalTime(Date estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }




}
