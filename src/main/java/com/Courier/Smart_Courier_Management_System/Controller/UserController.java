package com.Courier.Smart_Courier_Management_System.Controller;

import com.Courier.Smart_Courier_Management_System.DTO.ApiResponse;
import com.Courier.Smart_Courier_Management_System.DTO.RegisterRequest;
import com.Courier.Smart_Courier_Management_System.DTO.RegisterResponse;
import com.Courier.Smart_Courier_Management_System.Service.RegisterUser;
import com.Courier.Smart_Courier_Management_System.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RegisterUser register;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = register.register(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            true,
                            "User registered successfully",
                            response,
                            HttpStatus.CREATED.value()
                    ));
        } catch (Exception ex) {
            // Will be caught by GlobalExceptionHandler
            throw ex;
        }
    }
}
