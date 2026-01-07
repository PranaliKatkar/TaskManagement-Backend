package com.Verzat.VerzatTechno.Dto;

public class AdminResponse {
	private String username;
    private String role;

    public AdminResponse(String username, String role) {
        this.username = username;
        this.role = role;
    }
    public String getUsername() { return username; }
    public String getRole() { return role; }

}
