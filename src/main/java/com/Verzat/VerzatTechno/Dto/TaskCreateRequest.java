package com.Verzat.VerzatTechno.Dto;

public class TaskCreateRequest {

    private String title;

    public TaskCreateRequest() {
    }

    public TaskCreateRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
