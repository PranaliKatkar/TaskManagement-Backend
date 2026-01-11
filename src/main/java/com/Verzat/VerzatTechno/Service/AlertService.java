package com.Verzat.VerzatTechno.Service;

import com.Verzat.VerzatTechno.Entity.Task;
import com.Verzat.VerzatTechno.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    public void sendAlerts(User user, List<Task> tasks) {

        if (tasks.isEmpty()) return;

        StringBuilder msg = new StringBuilder("Today's Tasks:\n");

        for (Task t : tasks) {
            msg.append("- ").append(t.getTitle()).append("\n");
        }

        emailService.sendHtmlEmail(
                user.getEmail(),
                "Task Reminder",
                msg.toString()
        );

        smsService.sendSms(user.getContactNumber(), msg.toString());
    }
}
