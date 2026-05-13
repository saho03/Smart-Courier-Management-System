package com.Courier.Smart_Courier_Management_System.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class RegisterRequest {

    @NotBlank(message = "UserName is Required")
    @Size(min = 2,max = 50, message = "UserName must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be vaild")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8,message = "Password must be at least 8 character")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!*?])[A-Za-z\\d@$!%*?&]+$",
    message = "Password must contain uppercase,lowercase,number and Special Character")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
