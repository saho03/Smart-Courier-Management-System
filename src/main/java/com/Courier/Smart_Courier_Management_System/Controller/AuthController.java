package com.Courier.Smart_Courier_Management_System.Controller;

import com.Courier.Smart_Courier_Management_System.DTO.ApiResponse;
import com.Courier.Smart_Courier_Management_System.DTO.LoginRequest;
import com.Courier.Smart_Courier_Management_System.DTO.LoginResponse;
import com.Courier.Smart_Courier_Management_System.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Login Successful", response, HttpStatus.OK.value())
        );
    }
}