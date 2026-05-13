package com.Courier.Smart_Courier_Management_System.Repository;

import com.Courier.Smart_Courier_Management_System.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByCustomerId(int customerId);
}
