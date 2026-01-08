package com.Verzat.VerzatTechno.Scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.Verzat.VerzatTechno.Entity.Task;
import com.Verzat.VerzatTechno.Repository.TaskRepo;
import com.Verzat.VerzatTechno.Service.AlertService;

@Component
public class AlertScheduler {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AlertService alertService;

    @Transactional
    @Scheduled(cron = "0 10 12 * * ?")
    public void checkTasksForAlerts() {

        List<Task> pendingTasks = taskRepo.findByCompletedFalse();

        for (Task task : pendingTasks) {
            alertService.createAlertIfRequired(task);
        }
    }
}

