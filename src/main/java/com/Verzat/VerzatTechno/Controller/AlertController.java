package com.Verzat.VerzatTechno.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Verzat.VerzatTechno.Entity.Alert;
import com.Verzat.VerzatTechno.Repository.AlertRepository;

@RestController
@RequestMapping("/alerts")
@CrossOrigin(origins = "https://task-management-frontend-jk35.onrender.com")
public class AlertController {

  @Autowired
  private AlertRepository alertRepo;

  @GetMapping("/user/{email}")
  public List<Alert> getUserAlerts(@PathVariable String email) {
    return alertRepo.findByUserEmailOrderByCreatedAtDesc(email);
  }
}
