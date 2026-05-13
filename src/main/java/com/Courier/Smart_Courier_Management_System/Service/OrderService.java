package com.Courier.Smart_Courier_Management_System.Service;

import com.Courier.Smart_Courier_Management_System.DTO.OrderRequest;
import com.Courier.Smart_Courier_Management_System.DTO.OrderResponse;
import com.Courier.Smart_Courier_Management_System.Exception.LocationNotFound;
import com.Courier.Smart_Courier_Management_System.Exception.OrderNotFoundException;
import com.Courier.Smart_Courier_Management_System.Exception.UnauthorizedException;
import com.Courier.Smart_Courier_Management_System.Model.*;
import com.Courier.Smart_Courier_Management_System.Repository.LocationRepository;
import com.Courier.Smart_Courier_Management_System.Repository.OrderRepository;
import com.Courier.Smart_Courier_Management_System.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest request,int customerId) {
        User customer = userRepository.findById(customerId).orElseThrow(()-> new RuntimeException("Customer Not Found"));
        if (customer.getRole()!= UserRole.Customer) {
            throw new UnauthorizedException("Only customers can create orders");
        }
        Location senderLocation = locationRepository.findById(request.getSenderLocationId()).orElseThrow(()-> new LocationNotFound("Sender Location Not Found"));
        Location receiverLocation = locationRepository.findById(request.getReceiverLocationId())
                .orElseThrow(()-> new LocationNotFound("Receiver Location Not Found"));
        Order order = new Order();
        order.setCustomer(customer);
        order.setSenderLocation(senderLocation);
        order.setReceiverLocation(receiverLocation);
        order.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
        order.setStatus(OrderStatus.PENDING);

        Order saveOrder = orderRepository.save(order);
        return convertToResponse(saveOrder);
    }
    public OrderResponse getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order Not Found - Id:"+ orderId));

        return convertToResponse(order);
    }
    //Customer
    @Transactional
    public List<OrderResponse> getOrdersByCustomer(int customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        if (orders == null) {
            return new ArrayList<>();
        }
        return orders.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());    }

    //Admin and Manager
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateOrderStatus(int orderId,String status) {
        Order order = orderRepository.findById(orderId)
               .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        try {
            order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
            Order updateOrder = orderRepository.save(order);
            return convertToResponse(updateOrder);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
    private OrderResponse convertToResponse(Order order) {
        return new OrderResponse(order.getId(),
                order.getCustomer().getName(),
                order.getSenderLocation().getCity(),
                order.getReceiverLocation().getCity(),
                order.getOrderDate(),
                order.getExpectedDeliveryDate(),
                order.getStatus().name());
    }
}
