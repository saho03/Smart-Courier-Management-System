package com.Courier.Smart_Courier_Management_System.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "sender_location_id",nullable = false)
    private Location senderLocation;

    @ManyToOne
    @JoinColumn(name = "receiver_location_id",nullable = false)
    private Location receiverLocation;

    @Column(nullable = false,updatable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    private LocalDate expectedDeliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
    private OrderStatus status=OrderStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Location getSenderLocation() {
        return senderLocation;
    }

    public void setSenderLocation(Location senderLocation) {
        this.senderLocation = senderLocation;
    }

    public Location getReceiverLocation() {
        return receiverLocation;
    }

    public void setReceiverLocation(Location receiverLocation) {
        this.receiverLocation = receiverLocation;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
