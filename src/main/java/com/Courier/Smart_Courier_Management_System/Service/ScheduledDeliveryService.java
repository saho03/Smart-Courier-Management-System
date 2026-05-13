package com.Courier.Smart_Courier_Management_System.Service;

import com.Courier.Smart_Courier_Management_System.Model.DeliveryAssignment;
import com.Courier.Smart_Courier_Management_System.Model.DeliveryStatus;
import com.Courier.Smart_Courier_Management_System.Repository.DeliveryAssignmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class ScheduledDeliveryService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledDeliveryService.class);

    @Autowired
    private DeliveryAssignmentRepository deliveryAssignmentRepository;

     //1 RUN EVERY 30 SECONDS - Check and update in-transit deliveries
     //Simulates real-time tracking updates

    @Scheduled(fixedRate = 30000) // 30 seconds = 30000 ms
    public void updateInTransitDeliveries() {
        logger.info("[Scheduled Task] Running: Update in-transit deliveries...");

        try {
            List<DeliveryAssignment> inTransitAssignments =
                    deliveryAssignmentRepository.findByStatus(DeliveryStatus.IN_TRANSIT);

            logger.info("Found {} in-transit deliveries", inTransitAssignments.size());

            for (DeliveryAssignment assignment : inTransitAssignments) {
                // Simulate tracking update (in real app, this would be from GPS/API)
                logger.debug("Tracking: Assignment {} - Parcel {}",
                        assignment.getId(), assignment.getParcel().getId());
            }
        } catch (Exception e) {
            logger.error("Error in scheduled update: ", e);
        }
    }


     //2 RUN EVERY 1 MINUTE - Check for delayed deliveries
     // Alerts if delivery is overdue

    @Scheduled(fixedRate = 60000) // 60 seconds = 60000 ms
    public void checkForDelayedDeliveries() {
        logger.info("[Scheduled Task] Running: Check for delayed deliveries...");

        try {
            List<DeliveryAssignment> allAssignments =
                    deliveryAssignmentRepository.findAll();

            int delayedCount = 0;
            for (DeliveryAssignment assignment : allAssignments) {
                if (assignment.getStatus() == DeliveryStatus.IN_TRANSIT &&
                        assignment.getEstimatedDeliveryDate() != null &&
                        assignment.getEstimatedDeliveryDate().isBefore(java.time.LocalDateTime.now())) {

                    logger.warn("DELAYED: Assignment {} overdue by estimation", assignment.getId());
                    delayedCount++;

                    // Could send notification here
                    // notificationService.sendDelayAlert(assignment);
                }
            }

            if (delayedCount > 0) {
                logger.warn("Total delayed deliveries: {}", delayedCount);
            }
        } catch (Exception e) {
            logger.error("Error checking delays: ", e);
        }
    }


     //3 RUN EVERY 2 MINUTES - Generate delivery statistics
    // Shows progress and metrics

    @Scheduled(fixedRate = 120000) // 120 seconds = 120000 ms
    public void generateDeliveryStatistics() {
        logger.info("[Scheduled Task] Running: Generate delivery statistics...");

        try {
            List<DeliveryAssignment> allAssignments =
                    deliveryAssignmentRepository.findAll();

            if (allAssignments.isEmpty()) {
                logger.info("No assignments found");
                return;
            }

            long assigned = allAssignments.stream()
                    .filter(a -> a.getStatus() == DeliveryStatus.ASSIGNED)
                    .count();

            long inTransit = allAssignments.stream()
                    .filter(a -> a.getStatus() == DeliveryStatus.IN_TRANSIT)
                    .count();

            long delivered = allAssignments.stream()
                    .filter(a -> a.getStatus() == DeliveryStatus.DELIVERED)
                    .count();

            long failed = allAssignments.stream()
                    .filter(a -> a.getStatus() == DeliveryStatus.FAILED)
                    .count();

            logger.info("DELIVERY STATISTICS:");
            logger.info("Total Assignments: {}", allAssignments.size());
            logger.info("Assigned: {}", assigned);
            logger.info("In Transit: {}", inTransit);
            logger.info("Delivered: {}", delivered);
            logger.info("Failed: {}", failed);
            logger.info("Success Rate: {}%",
                    (delivered * 100 / allAssignments.size()));
        } catch (Exception e) {
            logger.error("Error generating statistics: ", e);
        }
    }


     //4 RUN AT SPECIFIC TIME - Daily summary (runs at 6 PM)
     //Format: second(0) minute(0) hour(18) - 18:00:00 = 6 PM

    @Scheduled(cron = "0 0 18 * * *")
    public void dailySummary() {
        logger.info("[Scheduled Task - Cron] Running: Daily summary at 6 PM...");

        try {
            List<DeliveryAssignment> allAssignments =
                    deliveryAssignmentRepository.findAll();

            long todayDelivered = allAssignments.stream()
                    .filter(a -> a.getStatus() == DeliveryStatus.DELIVERED &&
                            a.getActualDeliveryDate() != null &&
                            a.getActualDeliveryDate().isEqual(java.time.LocalDate.now()))
                    .count();

            logger.info("DAILY SUMMARY (Today):");
            logger.info("Delivered today: {}", todayDelivered);
            logger.info("Total assignments: {}", allAssignments.size());
        } catch (Exception e) {
            logger.error("Error in daily summary: ", e);
        }
    }


     //5 INITIAL DELAY - Runs after startup with delay
     //Example: Runs 10 seconds after app starts, then every 5 minutes

    @Scheduled(initialDelay = 10000, fixedRate = 300000)
    public void cleanupFailedDeliveries() {
        logger.info("[Scheduled Task] Running: Cleanup failed deliveries...");

        try {
            List<DeliveryAssignment> failedAssignments =
                    deliveryAssignmentRepository.findByStatus(
                            com.Courier.Smart_Courier_Management_System.Model.DeliveryStatus.FAILED);

            logger.info("Found {} failed assignments for review", failedAssignments.size());

            // Could implement retry logic here
            for (DeliveryAssignment assignment : failedAssignments) {
                logger.debug("Failed assignment {} needs attention", assignment.getId());
            }
        } catch (Exception e) {
            logger.error("Error in cleanup: ", e);
        }
    }
}
