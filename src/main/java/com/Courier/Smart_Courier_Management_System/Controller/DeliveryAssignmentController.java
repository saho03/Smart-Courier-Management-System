package com.Courier.Smart_Courier_Management_System.Controller;

import com.Courier.Smart_Courier_Management_System.DTO.*;
import com.Courier.Smart_Courier_Management_System.Secutiy.JwtTokenProvider;
import com.Courier.Smart_Courier_Management_System.Service.DeliveryAssignmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*")
public class DeliveryAssignmentController {

    @Autowired
    private DeliveryAssignmentService deliveryAssignmentService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<DeliveryAssignmentResponse>> assignDelivery(
            @Valid @RequestBody DeliveryAssignmentRequest request,
            @RequestHeader("Authorization") String token) {

        DeliveryAssignmentResponse response = deliveryAssignmentService.assignDelivery(request);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Assignment Retrieved Successfully", response, 200)
        );
    }

    @PostMapping("/bulk-assign")
    public ResponseEntity<ApiResponse<BulkAssignmentResponse>> bulkAssignDeliveries(
            @Valid @RequestBody BulkAssignmentRequest request,
            @RequestHeader("Authorization") String token) {

        BulkAssignmentResponse response = deliveryAssignmentService.bulkAssignDeliveries(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Bulk Assignments Processed: " + response.getSuccessfulAssignments()
                                + " Successful, " + response.getFailedAssignments() + " Failed",
                        response,
                        201
                ));
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<ApiResponse<DeliveryAssignmentResponse>> getAssignmentById(
            @PathVariable int assignmentId,
            @RequestHeader("Authorization") String token) {

        DeliveryAssignmentResponse response = deliveryAssignmentService.getAssignmentById(assignmentId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Assignment Retrieved Successfully", response, 200)
        );
    }

    // FIX: Corrected typo in path — was "/my-aassignments" (double 'a')
    // FIX: Corrected JWT strip — was missing trailing space after "Bearer"
    @GetMapping("/my-assignments")
    public ResponseEntity<ApiResponse<List<DeliveryAssignmentResponse>>> getMyAssignments(
            @RequestHeader("Authorization") String token) {

        String jwt = token.replace("Bearer ", "");  // FIX: added space after "Bearer"
        int agentId = jwtTokenProvider.getUserIdFromToken(jwt);

        List<DeliveryAssignmentResponse> responses = deliveryAssignmentService.getMyAssignments(agentId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Your Assignments Retrieved: " + responses.size() + " Found",
                        responses,
                        200
                )
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DeliveryAssignmentResponse>>> getAllAssignments(
            @RequestHeader("Authorization") String token) {

        List<DeliveryAssignmentResponse> responses = deliveryAssignmentService.getAllAssignments();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "All Assignments Retrieved: " + responses.size() + " Found",  // FIX: was "Foudn"
                        responses,
                        200
                )
        );
    }

    @PutMapping("/{assignmentId}/status")
    public ResponseEntity<ApiResponse<DeliveryAssignmentResponse>> updateAssignmentStatus(
            @PathVariable int assignmentId,
            @RequestParam String status,
            @RequestHeader("Authorization") String token) {

        DeliveryAssignmentResponse response = deliveryAssignmentService.updateAssignmentStatus(assignmentId, status);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Status Updated to: " + status, response, 200)
        );
    }

    @PutMapping("/{assignmentId}/location/{locationId}")
    public ResponseEntity<ApiResponse<DeliveryAssignmentResponse>> updateCurrentLocation(
            @PathVariable int assignmentId,
            @PathVariable int locationId,
            @RequestHeader("Authorization") String token) {

        DeliveryAssignmentResponse response = deliveryAssignmentService.updateCurrentLocation(assignmentId, locationId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Current Location Updated", response, 200)
        );
    }

    @GetMapping("/in-transit")
    public ResponseEntity<ApiResponse<List<DeliveryAssignmentResponse>>> getInTransitAssignments(
            @RequestHeader("Authorization") String token) {

        List<DeliveryAssignmentResponse> responses = deliveryAssignmentService.getInTransitAssignments();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "In-Transit Assignments: " + responses.size(),
                        responses,
                        200
                )
        );
    }
}