package com.Verzat.VerzatTechno.Service;

import com.Verzat.VerzatTechno.Entity.Task;
import com.Verzat.VerzatTechno.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    public void sendAlerts(User user, List<Task> tasks) {

        if (tasks.isEmpty()) return;

        LocalDate today = LocalDate.now();
        StringBuilder msg = new StringBuilder();

        for (Task task : tasks) {

            if (task.getDueDate() == null) continue;

            if (task.getDueDate().isEqual(today)) {
                msg.append("Task \"")
                   .append(task.getTitle())
                   .append("\" is due today\n");
            }
            else if (task.getDueDate().isEqual(today.plusDays(1))) {
                msg.append("Task \"")
                   .append(task.getTitle())
                   .append("\" is due tomorrow\n");
            }
        }

        if (msg.length() == 0) return;

        emailService.sendHtmlEmail(
                user.getEmail(),
                "Task Reminder",
                msg.toString()
        );

        smsService.sendSms(user.getContactNumber(), msg.toString());
    }
}