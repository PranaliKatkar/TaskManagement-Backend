package com.Verzat.VerzatTechno.Controller;

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
    public void updateSettings(
            @RequestParam String email,
            @RequestParam boolean enabled,
            @RequestParam(required = false) String time) {

        User user = userRepo.findByEmail(email).orElseThrow();

        user.setAlertsEnabled(enabled);
        if (enabled && time != null) {
            user.setAlertTime(LocalTime.parse(time));
        }

        userRepo.save(user);
    }
}
