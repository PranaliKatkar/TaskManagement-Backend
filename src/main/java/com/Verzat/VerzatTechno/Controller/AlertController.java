package com.Verzat.VerzatTechno.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Verzat.VerzatTechno.Entity.Alert;
import com.Verzat.VerzatTechno.Repository.AlertRepository;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "https://task-management-frontend-jk35.onrender.com")
public class AlertController {

    @Autowired
    private AlertRepository alertRepo;

    @GetMapping("/user/{email}")
    public List<Alert> getUserAlerts(@PathVariable String email) {
        return alertRepo.findByUserEmailOrderByCreatedAtDesc(email);
    }

    @DeleteMapping("/{alertId}")
    public void deleteAlert(@PathVariable Long alertId) {
        alertRepo.deleteById(alertId);
    }
}
