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

        return userRepo.findByEmail(email)
           //     .filter(User::isAlertEnabled)
                .map(u -> alertRepo.findByUserEmailOrderByCreatedAtDesc(email))
                .orElse(List.of());
    }

    @DeleteMapping("/{alertId}")
    public void deleteAlert(@PathVariable Long alertId) {
        alertRepo.deleteById(alertId);
    }
}
