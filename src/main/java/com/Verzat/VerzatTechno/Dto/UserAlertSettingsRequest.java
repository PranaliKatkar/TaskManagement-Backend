package com.Verzat.VerzatTechno.Dto;


public class UserAlertSettingsRequest {

    private String email;
    private boolean enabled;
    private String time;

    public String getEmail() {
        return email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getTime() {
        return time;
    }
}
