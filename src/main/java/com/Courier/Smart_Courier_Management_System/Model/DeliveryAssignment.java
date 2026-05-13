package com.Courier.Smart_Courier_Management_System.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

// ✅ FIX: Correct Location import
import com.Courier.Smart_Courier_Management_System.Model.Location;

@Entity
@Table(name = "delivery_assignments")
public class DeliveryAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Parcel parcel;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private User agent;

    @Column(nullable = false, updatable = false)
    private LocalDateTime assignedDate = LocalDateTime.now();

    private LocalDateTime estimatedDeliveryDate;
    private LocalDate actualDeliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'ASSIGNED'")
    private DeliveryStatus status = DeliveryStatus.ASSIGNED;

    // ✅ FIX: Now using correct Location entity
    @ManyToOne
    @JoinColumn(name = "current_location_id")
    private Location currentLocation;

    public DeliveryAssignment(int id, Parcel parcel, User agent, LocalDateTime assignedDate, LocalDateTime estimatedDeliveryDate, LocalDate actualDeliveryDate, DeliveryStatus status, Location currentLocation) {
        this.id = id;
        this.parcel = parcel;
        this.agent = agent;
        this.assignedDate = assignedDate;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.actualDeliveryDate = actualDeliveryDate;
        this.status = status;
        this.currentLocation = currentLocation;
    }

    public DeliveryAssignment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    public LocalDateTime getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDateTime assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDateTime getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public LocalDate getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(LocalDate actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setCurrentLocation(javax.xml.stream.Location currentLocation) {
    }
}