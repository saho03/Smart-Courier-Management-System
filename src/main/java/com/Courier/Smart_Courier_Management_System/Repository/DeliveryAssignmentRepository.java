package com.Courier.Smart_Courier_Management_System.Repository;

import com.Courier.Smart_Courier_Management_System.Model.DeliveryAssignment;
import com.Courier.Smart_Courier_Management_System.Model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryAssignmentRepository extends JpaRepository<DeliveryAssignment,Integer> {
        List<DeliveryAssignment> findByAgent_Id(int agentId);

        List<DeliveryAssignment> findByStatus(DeliveryStatus status);
}
//List<DeliveryAssignment> findByAgent_Id(int agentId);
//
//
//    List<DeliveryAssignment> findByStatus(DeliveryStatus status);