package com.Verzat.VerzatTechno.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private EmailService emailService;

    @Transactional
    public void regenerateAllAlerts(List<Task> tasks, LocalDate today) {

        alertRepo.deleteAll();

        for (Task task : tasks) {
            generateAlertForTask(task, today);
        }

        sendMailIfAlertsExist(today);
    }

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

    private void sendMailIfAlertsExist(LocalDate today) {

        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(23, 59, 59);

        List<Alert> alerts = alertRepo.findByCreatedAtBetween(start, end);
        if (alerts.isEmpty()) return;

        Map<String, List<Alert>> grouped =
                alerts.stream().collect(Collectors.groupingBy(Alert::getUserEmail));

        grouped.forEach((email, userAlerts) -> {

            StringBuilder html = new StringBuilder();
            html.append("<h3>Your Task Alerts</h3><ul>");

            for (Alert alert : userAlerts) {
                html.append("<li>").append(alert.getMessage()).append("</li>");
            }

            html.append("</ul>");

            try {
                emailService.sendHtmlEmail(
                        email,
                        "Your Task Alerts – " + today,
                        html.toString()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
