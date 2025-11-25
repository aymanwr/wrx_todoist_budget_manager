package com.tbm.task_budget_manager.task.dto;

import com.tbm.task_budget_manager.task.TaskStatus;
import jakarta.validation.constraints.Size;

public class UpdateTaskRequest {

    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    private TaskStatus status; // TODO or DONE

    // getters/setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}
