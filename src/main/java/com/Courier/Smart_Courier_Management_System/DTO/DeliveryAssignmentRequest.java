package com.Courier.Smart_Courier_Management_System.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DeliveryAssignmentRequest {

    @NotNull(message = "Parcel ID is required")
    private Integer parcelId;

    @NotNull(message = "Agent ID is required")
    private Integer agentId;

    private Integer currentLocationId;

    private LocalDate estimatedDeliveryDate;

    public DeliveryAssignmentRequest() {
    }

    public DeliveryAssignmentRequest(Integer parcelId, Integer agentId, Integer currentLocationId, LocalDate estimatedDeliveryDate) {
        this.parcelId = parcelId;
        this.agentId = agentId;
        this.currentLocationId = currentLocationId;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public Integer getParcelId() {
        return parcelId;
    }

    public void setParcelId(Integer parcelId) {
        this.parcelId = parcelId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getCurrentLocationId() {
        return currentLocationId;
    }

    public void setCurrentLocationId(Integer currentLocationId) {
        this.currentLocationId = currentLocationId;
    }

    public LocalDate getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }
}
