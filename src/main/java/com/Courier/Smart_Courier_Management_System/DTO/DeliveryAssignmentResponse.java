package com.Courier.Smart_Courier_Management_System.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DeliveryAssignmentResponse {
    private int assignmentId;
    private int parcelId;
    private String agentName;
    private String agentEmail;
    private LocalDateTime assignedDate;
    private LocalDate estimatedDeliveryDate;
    private LocalDate actualDeliveryDate;
    private String status;
    private String currentLocation;

    public DeliveryAssignmentResponse() {

    }

    public DeliveryAssignmentResponse(int assignmentId, int parcelId, String agentName, String agentEmail, LocalDateTime assignedDate, LocalDate estimatedDeliveryDate, LocalDate actualDeliveryDate, String status, String currentLocation) {
        this.assignmentId = assignmentId;
        this.parcelId = parcelId;
        this.agentName = agentName;
        this.agentEmail = agentEmail;
        this.assignedDate = assignedDate;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.actualDeliveryDate = actualDeliveryDate;
        this.status = status;
        this.currentLocation = currentLocation;
    }

    public DeliveryAssignmentResponse(int id, int id1, String name, String email, LocalDateTime assignedDate, LocalDateTime estimatedDeliveryDate, LocalDate actualDeliveryDate, String name1, String currentLocation) {
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getParcelId() {
        return parcelId;
    }

    public void setParcelId(int parcelId) {
        this.parcelId = parcelId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public LocalDateTime getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDateTime assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDate getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public LocalDate getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(LocalDate actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
}
