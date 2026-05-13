package com.Courier.Smart_Courier_Management_System.DTO;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class BulkAssignmentRequest {
    @NotEmpty(message = "Assignments list cannot be empty")
    private List<DeliveryAssignmentRequest> assignments;

    public BulkAssignmentRequest(List<DeliveryAssignmentRequest> assignments) {
        this.assignments = assignments;
    }

    public List<DeliveryAssignmentRequest> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<DeliveryAssignmentRequest> assignments) {
        this.assignments = assignments;
    }
}
