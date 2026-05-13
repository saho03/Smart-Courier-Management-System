package com.Courier.Smart_Courier_Management_System.Service;

import com.Courier.Smart_Courier_Management_System.DTO.LoginRequest;
import com.Courier.Smart_Courier_Management_System.DTO.LoginResponse;
import com.Courier.Smart_Courier_Management_System.Exception.InvalidCredentialsException;
import com.Courier.Smart_Courier_Management_System.Model.User;
import com.Courier.Smart_Courier_Management_System.Repository.UserRepository;
import com.Courier.Smart_Courier_Management_System.Secutiy.JwtTokenProvider;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;


@Service
public class AuthService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final Logger logger= LoggerFactory.getLogger(AuthService.class);


    public LoginResponse login(LoginRequest request) {
        logger.info("Login attempt for email: {}",request.getEmail());

        String email= request.getEmail().trim().toLowerCase();
        User user = userRepository.findByEmail(email).orElseThrow(()->{
            logger.warn("Login failed: User not found  - {}",email);
            return new InvalidCredentialsException("Invalid email and password");
        });
        if(!encoder.matches(request.getPassword(),user.getPassword())){
            logger.warn("Login failed: Wrong password for - {}",email);
            throw new InvalidCredentialsException("Invalid email and password");
        }
        String token = jwtTokenProvider.generateToken(user.getId(),
                user.getEmail(),
                user.getRole().name());
        logger.info("User logged in successfully: {} ({})",user.getName(),user.getRole());

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                token
        );
    }
}

