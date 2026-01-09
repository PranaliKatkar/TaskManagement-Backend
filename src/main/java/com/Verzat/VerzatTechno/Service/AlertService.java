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
import com.Verzat.VerzatTechno.Repository.UserRepo;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SmsService smsService;

    @Transactional
    public void regenerateAlertsForUser(List<Task> tasks, LocalDate today, String userEmail) {

        alertRepo.deleteByUserEmail(userEmail);

        for (Task task : tasks) {
            if (!task.getFolder().getUser().getEmail().equals(userEmail)) continue;
            generateAlertForTask(task, today);
        }

        sendMailIfAlertsExist(today, userEmail);
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

    private void sendMailIfAlertsExist(LocalDate today, String userEmail) {

        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(23, 59, 59);

        List<Alert> alerts =
                alertRepo.findByCreatedAtBetween(start, end)
                         .stream()
                         .filter(a -> a.getUserEmail().equals(userEmail))
                         .toList();

        if (alerts.isEmpty()) return;

        StringBuilder html = new StringBuilder();
        html.append("<h3>Your Task Alerts</h3><ul>");

        for (Alert alert : alerts) {
            html.append("<li>").append(alert.getMessage()).append("</li>");
        }

        html.append("</ul>");

        emailService.sendHtmlEmail(
                userEmail,
                "Your Task Alerts – " + today,
                html.toString()
        );

        userRepo.findByEmail(userEmail).ifPresent(user -> {

            StringBuilder smsText = new StringBuilder();
            smsText.append("Task Alerts:\n");

            for (Alert alert : alerts) {
                smsText.append("- ")
                       .append(alert.getMessage())
                       .append("\n");
            }

            smsService.sendSms(
                    user.getPhoneNumber(),
                    smsText.toString()
            );
        });
    }
}
