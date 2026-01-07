package com.Verzat.VerzatTechno.Dto;

import com.Verzat.VerzatTechno.Entity.Task;

public class TaskResponse {
    private Long id;
    private String title;
    private boolean completed;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.completed = task.isCompleted();
    }

    // getters only
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }
}
