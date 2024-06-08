package com.tobeto_case.tobeto_case.services.dtos.response;

import com.tobeto_case.tobeto_case.entities.Task;
import com.tobeto_case.tobeto_case.entities.TaskStatus;

import java.util.Date;

public record TaskResponse(Long id, String title, String description, Date createdDate, TaskStatus status) {
    public TaskResponse(Task task) {
        this(task.getId(), task.getTitle(), task.getDescription(), task.getCreatedDate(), task.getStatus());
    }
}