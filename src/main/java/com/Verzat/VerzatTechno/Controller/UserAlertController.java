package com.Verzat.VerzatTechno.Controller;

import com.Verzat.VerzatTechno.Dto.UserAlertSettingsRequest;
import com.Verzat.VerzatTechno.Dto.UserAlertSettingsResponse;
import com.Verzat.VerzatTechno.Entity.User;
import com.Verzat.VerzatTechno.Repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/user/alert")
@CrossOrigin
public class UserAlertController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/settings/{email}")
    public UserAlertSettingsResponse getSettings(@PathVariable String email) {

        User user = userRepo.findByEmail(email).orElseThrow();

        return new UserAlertSettingsResponse(
                user.isAlertsEnabled(),
                user.getAlertTime() != null ? user.getAlertTime().toString() : ""
        );
    }

    @PostMapping("/settings")
    public ResponseEntity<String> updateSettings(
            @RequestBody UserAlertSettingsRequest req) {

        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAlertsEnabled(req.isEnabled());

        if (req.isEnabled()) {
            if (req.getTime() == null || req.getTime().isBlank()) {
                return ResponseEntity.badRequest().body("Time required");
            }
            user.setAlertTime(LocalTime.parse(req.getTime()));
        } else {
            user.setAlertTime(null);
        }

        userRepo.save(user);
        return ResponseEntity.ok("Alert set successfully");
    }
}
