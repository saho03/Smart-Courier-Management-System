package com.Courier.Smart_Courier_Management_System.Service;

import com.Courier.Smart_Courier_Management_System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

}