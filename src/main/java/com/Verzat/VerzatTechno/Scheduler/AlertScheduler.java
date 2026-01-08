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

    @Scheduled(cron = "0 20 13 * * ?", zone = "Asia/Kolkata")
    public void generateDailyAlerts() {

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        LocalDate tomorrow = today.plusDays(1);

        List<Task> tasks = taskRepo.findByDueDateIn(
                List.of(today, tomorrow)
        );

        System.out.println("Scheduler running at 1 PM");
        System.out.println("Tasks found: " + tasks.size());

        for (Task task : tasks) {

            Alert alert = new Alert();
            alert.setTaskId(task.getId());

            alert.setUserEmail(
                task.getFolder().getUser().getEmail()
            );

            alert.setAlertType("DUE");

            alert.setMessage(
                task.getDueDate().equals(today)
                    ? "Task due TODAY: " + task.getTitle()
                    : "Task due TOMORROW: " + task.getTitle()
            );

            alertRepository.save(alert);
        }
    }
}
