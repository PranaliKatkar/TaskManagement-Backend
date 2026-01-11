package com.Verzat.VerzatTechno.Scheduler;

import com.Verzat.VerzatTechno.Entity.Task;
import com.Verzat.VerzatTechno.Entity.User;
import com.Verzat.VerzatTechno.Repository.TaskRepo;
import com.Verzat.VerzatTechno.Repository.UserRepo;
import com.Verzat.VerzatTechno.Service.AlertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@EnableScheduling
public class AlertScheduler {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AlertService alertService;

    @Scheduled(cron = "0 * * * * *")
    public void executeAlerts() {

        LocalTime now = LocalTime.now().withSecond(0).withNano(0);
        LocalDate today = LocalDate.now();

        for (User user : userRepo.findAll()) {

            if (!user.isAlertsEnabled()) continue;
            if (user.getAlertTime() == null) continue;

            if (user.getAlertTime().withSecond(0).withNano(0).equals(now)) {

                List<Task> tasks =
                        taskRepo.findByFolder_User_EmailAndDueDate(
                                user.getEmail(), today);

                alertService.sendAlerts(user, tasks);
            }
        }
    }
}
