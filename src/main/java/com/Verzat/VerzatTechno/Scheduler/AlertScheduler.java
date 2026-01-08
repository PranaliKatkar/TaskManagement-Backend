package com.Verzat.VerzatTechno.Scheduler;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.Verzat.VerzatTechno.Entity.Alert;
import com.Verzat.VerzatTechno.Entity.Task;
import com.Verzat.VerzatTechno.Repository.AlertRepository;
import com.Verzat.VerzatTechno.Repository.TaskRepo;
import com.Verzat.VerzatTechno.Service.AlertService;

@Component
public class AlertScheduler {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AlertRepository alertRepository;

    @Transactional
    @Scheduled(cron = "0 15 14 * * ?", zone = "Asia/Kolkata")
    public void generateDailyAlerts() {

        System.out.println("=== ALERT SCHEDULER STARTED ===");

        alertRepository.deleteAll();
        System.out.println("Old alerts cleared");

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        LocalDate tomorrow = today.plusDays(1);

        List<Task> allTasks = taskRepo.findAll();
        System.out.println("Total tasks found: " + allTasks.size());

        for (Task task : allTasks) {

            LocalDate dueDate = task.getDueDate();
            String alertType = null;
            String message = null;

            if (dueDate.isBefore(today)) {
                alertType = "OVERDUE";
                message = "Task \"" + task.getTitle() + "\" is OVERDUE";
            }
            else if (dueDate.isEqual(today)) {
                alertType = "TODAY";
                message = "Task due TODAY: " + task.getTitle();
            }
            else if (dueDate.isEqual(tomorrow)) {
                alertType = "TOMORROW";
                message = "Task due TOMORROW: " + task.getTitle();
            }

            if (alertType != null) {
                Alert alert = new Alert();
                alert.setTaskId(task.getId());
                alert.setUserEmail(task.getFolder().getUser().getEmail());
                alert.setAlertType(alertType);
                alert.setMessage(message);

                alertRepository.save(alert);
            }
        }

        System.out.println("=== ALERT SCHEDULER FINISHED ===");
    }
}
