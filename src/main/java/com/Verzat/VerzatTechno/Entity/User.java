package com.Verzat.VerzatTechno.Entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String contactNumber;

    private boolean alertsEnabled;
    private LocalTime alertTime;

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getContactNumber() { return contactNumber; }

    public boolean isAlertsEnabled() { return alertsEnabled; }
    public LocalTime getAlertTime() { return alertTime; }

    public void setAlertsEnabled(boolean alertsEnabled) {
        this.alertsEnabled = alertsEnabled;
    }

    public void setAlertTime(LocalTime alertTime) {
        this.alertTime = alertTime;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
