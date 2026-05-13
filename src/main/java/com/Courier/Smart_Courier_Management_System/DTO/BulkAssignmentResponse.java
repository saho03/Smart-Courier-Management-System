package com.Courier.Smart_Courier_Management_System.DTO;

import java.util.List;

public class BulkAssignmentResponse {
    private int totalRequested;
    private int successfulAssignments;
    private int failedAssignments;
    private long processingTimeMs;
    private List<String> successMessages;
    private List<String> errorMessages;

    public BulkAssignmentResponse(int totalRequested, int successfulAssignments, int failedAssignments, long processingTimeMs, List<String> successMessages, List<String> errorMessages) {
        this.totalRequested = totalRequested;
        this.successfulAssignments = successfulAssignments;
        this.failedAssignments = failedAssignments;
        this.processingTimeMs = processingTimeMs;
        this.successMessages = successMessages;
        this.errorMessages = errorMessages;
    }

    public int getTotalRequested() {
        return totalRequested;
    }

    public void setTotalRequested(int totalRequested) {
        this.totalRequested = totalRequested;
    }

    public int getSuccessfulAssignments() {
        return successfulAssignments;
    }

    public void setSuccessfulAssignments(int successfulAssignments) {
        this.successfulAssignments = successfulAssignments;
    }

    public int getFailedAssignments() {
        return failedAssignments;
    }

    public void setFailedAssignments(int failedAssignments) {
        this.failedAssignments = failedAssignments;
    }

    public long getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(long processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }

    public List<String> getSuccessMessages() {
        return successMessages;
    }

    public void setSuccessMessages(List<String> successMessages) {
        this.successMessages = successMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
