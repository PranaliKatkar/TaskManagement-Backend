package com.Verzat.VerzatTechno.Dto;


public class UserAlertSettingsResponse {

    private boolean alertEnabled;
    private String alertTime;

    public UserAlertSettingsResponse(boolean alertEnabled, String alertTime) {
        this.alertEnabled = alertEnabled;
        this.alertTime = alertTime;
    }

    public boolean isAlertEnabled() {
        return alertEnabled;
    }

    public String getAlertTime() {
        return alertTime;
    }
}
