package com.tbm.task_budget_manager.task.dto;

import com.tbm.task_budget_manager.task.Task;
import com.tbm.task_budget_manager.task.TaskStatus;

import java.time.LocalDateTime;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;

    public static TaskResponse of(Task t) {
        TaskResponse r = new TaskResponse();
        r.id = t.getId();
        r.title = t.getTitle();
        r.description = t.getDescription();
        r.status = t.getStatus();
        r.createdAt = t.getCreatedAt();
        return r;
    }

    // getters only (optional setters if you need)
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
