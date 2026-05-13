package com.Courier.Smart_Courier_Management_System.Service;

import com.Courier.Smart_Courier_Management_System.DTO.RegisterRequest;
import com.Courier.Smart_Courier_Management_System.DTO.RegisterResponse;
import com.Courier.Smart_Courier_Management_System.Exception.PasswordMismatchException;
import com.Courier.Smart_Courier_Management_System.Exception.UserAlreadyExistsException;
import com.Courier.Smart_Courier_Management_System.Model.User;
import com.Courier.Smart_Courier_Management_System.Model.UserRole;
import com.Courier.Smart_Courier_Management_System.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUser {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        //Normalize input
        String name = request.getName().trim();
        String email = request.getEmail().trim().toLowerCase();

        //Check if Email is already exists
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        //Validate Password Match
        if (request.getConfirmPassword() == null ||
            !request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException("Password do not Match");
        }

        //Restrict Role
        UserRole role = UserRole.Customer;

        //Hash Password
        String encodedPassword = encoder.encode(request.getPassword());

        //Create Entity
        User user = new User(name,email,encodedPassword,role);

        //Save
        userRepository.save(user);

        RegisterResponse users = new RegisterResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
        return users;
    }
}
