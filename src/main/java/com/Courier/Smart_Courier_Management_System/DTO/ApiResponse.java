package com.Courier.Smart_Courier_Management_System.DTO;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private int status;
    private LocalDateTime timestamp;

    // Full constructor
    public ApiResponse(boolean success, String message, T data, int status, LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = status;
        this.timestamp = timestamp;
    }

    // 4-arg constructor (auto-sets timestamp)
    public ApiResponse(boolean success, String message, T data, int status) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    // Default constructor
    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}