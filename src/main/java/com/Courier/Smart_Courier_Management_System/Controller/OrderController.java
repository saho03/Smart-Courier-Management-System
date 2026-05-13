package com.Courier.Smart_Courier_Management_System.Controller;

import com.Courier.Smart_Courier_Management_System.DTO.ApiResponse;
import com.Courier.Smart_Courier_Management_System.DTO.OrderRequest;
import com.Courier.Smart_Courier_Management_System.DTO.OrderResponse;
import com.Courier.Smart_Courier_Management_System.Secutiy.JwtTokenProvider;
import com.Courier.Smart_Courier_Management_System.Service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// FIX: Added missing @RestController and @RequestMapping annotations
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @RequestBody OrderRequest request,
            @RequestHeader("Authorization") String token) {

        String jwt = token.replace("Bearer ", "");  // FIX: added space after "Bearer"
        log.info("token: "+jwt);
        int customerId = jwtTokenProvider.getUserIdFromToken(jwt);
        log.info("customerId: "+customerId);
        OrderResponse response = orderService.createOrder(request, customerId);
        log.info("order Created");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Order Created Successfully", response, 201));
    }

    // FIX: Was @GetMapping("/{orderId") — missing closing brace
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(
            @PathVariable int orderId,
            @RequestHeader("Authorization") String token) {

        OrderResponse response = orderService.getOrderById(orderId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Order Retrieved Successfully", response, 200)
        );
    }

    @GetMapping("/myOrders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getMyOrders(
            @RequestHeader("Authorization") String token) {

        String jwt = token.replace("Bearer ", "");  // FIX: added space after "Bearer"
        int customerId = jwtTokenProvider.getUserIdFromToken(jwt);
        List<OrderResponse> orders = orderService.getOrdersByCustomer(customerId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Orders Retrieved Successfully", orders, 200)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders(
            @RequestHeader("Authorization") String token) {

        List<OrderResponse> orders = orderService.getAllOrders();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "All Orders Retrieved Successfully", orders, 200)
        );
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable int orderId,
            @RequestParam String status,
            @RequestHeader("Authorization") String token) {

        OrderResponse response = orderService.updateOrderStatus(orderId, status);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Order Status Updated Successfully", response, 200)
        );
    }
}