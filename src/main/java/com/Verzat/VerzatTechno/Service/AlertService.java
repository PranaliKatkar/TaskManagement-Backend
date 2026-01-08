package com.Verzat.VerzatTechno.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Verzat.VerzatTechno.Entity.Alert;
import com.Verzat.VerzatTechno.Entity.Task;
import com.Verzat.VerzatTechno.Repository.AlertRepository;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepo;

    @Transactional
    public void generateAlertForTask(Task task, LocalDate today) {

        if (task == null || task.isCompleted() || task.getDueDate() == null) {
            return;
        }

        LocalDate dueDate = task.getDueDate();
        String alertType = null;
        String message = null;

        if (dueDate.isBefore(today)) {
            alertType = "OVERDUE";
            message = "Task \"" + task.getTitle() + "\" is overdue";
        } else if (dueDate.isEqual(today)) {
            alertType = "TODAY";
            message = "Task \"" + task.getTitle() + "\" is due today";
        } else if (dueDate.isEqual(today.plusDays(1))) {
            alertType = "TOMORROW";
            message = "Task \"" + task.getTitle() + "\" is due tomorrow";
        }

        if (alertType == null) return;

        alertRepo.deleteByTaskId(task.getId());

        Alert alert = new Alert();
        alert.setTaskId(task.getId());
        alert.setUserEmail(task.getFolder().getUser().getEmail());
        alert.setAlertType(alertType);
        alert.setMessage(message);

        alertRepo.save(alert);
    }

    @Transactional
    public void regenerateAllAlerts(List<Task> tasks, LocalDate today) {
        alertRepo.deleteAll();
        for (Task task : tasks) {
            generateAlertForTask(task, today);
        }
    }
}
