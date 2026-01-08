package com.Verzat.VerzatTechno.Scheduler;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.Verzat.VerzatTechno.Entity.Task;
import com.Verzat.VerzatTechno.Repository.TaskRepo;
import com.Verzat.VerzatTechno.Service.AlertService;

@Component
public class AlertScheduler {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AlertService alertService;

    @Scheduled(cron = "0 10 17 * * ?", zone = "Asia/Kolkata")
    public void runDailyAlertJob() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        List<Task> allTasks = taskRepo.findAll();
        alertService.regenerateAllAlerts(allTasks, today);
    }
}
