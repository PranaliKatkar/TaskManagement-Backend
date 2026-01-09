package com.Verzat.VerzatTechno.Scheduler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.Verzat.VerzatTechno.Entity.Task;
import com.Verzat.VerzatTechno.Entity.User;
import com.Verzat.VerzatTechno.Repository.TaskRepo;
import com.Verzat.VerzatTechno.Repository.UserRepo;
import com.Verzat.VerzatTechno.Service.AlertService;

@Component
public class AlertScheduler {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AlertService alertService;

    @Autowired
    private UserRepo userRepo;

    @Scheduled(cron = "0 * * * * ?", zone = "Asia/Kolkata")
    public void runUserTimeAlerts() {

        LocalTime now = LocalTime.now().withSecond(0);
        LocalDate today = LocalDate.now();
        List<Task> tasks = taskRepo.findAll();
        List<User> users = userRepo.findByAlertEnabledTrue();

        for (User user : users) {
            if (user.getAlertTime() != null && user.getAlertTime().equals(now)) {
                alertService.regenerateAlertsForUser(
                        tasks,
                        today,
                        user.getEmail()
                );
            }
        }
    }
}
