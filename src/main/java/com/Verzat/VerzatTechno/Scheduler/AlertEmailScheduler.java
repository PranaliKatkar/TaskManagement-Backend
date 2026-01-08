package com.Verzat.VerzatTechno.Scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.Verzat.VerzatTechno.Entity.Alert;
import com.Verzat.VerzatTechno.Repository.AlertRepository;
import com.Verzat.VerzatTechno.Service.EmailService;

@Component
public class AlertEmailScheduler {

    @Autowired
    private AlertRepository alertRepo;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 50 15 * * ?", zone = "Asia/Kolkata")
    public void sendDailyAlertEmails() {

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        LocalDateTime start =
                today.atStartOfDay(ZoneId.of("Asia/Kolkata")).toLocalDateTime();

        LocalDateTime end =
                today.atTime(23, 59, 59);

        List<Alert> alerts =
                alertRepo.findByCreatedAtBetween(start, end);

        if (alerts.isEmpty()) {
            return;
        }

        Map<String, List<Alert>> alertsByUser =
                alerts.stream()
                        .collect(Collectors.groupingBy(Alert::getUserEmail));

        for (Map.Entry<String, List<Alert>> entry : alertsByUser.entrySet()) {

            String email = entry.getKey();
            List<Alert> userAlerts = entry.getValue();

            StringBuilder html = new StringBuilder();
            html.append("<h3>Good Morning</h3>");
            html.append("<p>Here are your task alerts for today:</p>");
            html.append("<ul>");

            for (Alert alert : userAlerts) {
                html.append("<li>")
                    .append(alert.getMessage())
                    .append("</li>");
            }

            html.append("</ul>");
            html.append("<br/><p>Regards,<br/>Task Management System</p>");

            try {
                emailService.sendHtmlEmail(
                        email,
                        "Your Task Alerts – " + today,
                        html.toString()
                );
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        }
    }
}

