package com.Verzat.VerzatTechno.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Verzat.VerzatTechno.Entity.Alert;
import com.Verzat.VerzatTechno.Entity.User;
import com.Verzat.VerzatTechno.Repository.AlertRepository;
import com.Verzat.VerzatTechno.Repository.UserRepo;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "https://task-management-frontend-jk35.onrender.com")
public class AlertController {

    @Autowired
    private AlertRepository alertRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/user/{email}")
    public List<Alert> getUserAlerts(@PathVariable String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isAlertsEnabled()) {
            return List.of();
        }

        return alertRepo.findByUserEmailOrderByCreatedAtDesc(email);
    }

    @DeleteMapping("/{alertId}")
    public void deleteAlert(@PathVariable Long alertId) {
        alertRepo.deleteById(alertId);
    }
}
