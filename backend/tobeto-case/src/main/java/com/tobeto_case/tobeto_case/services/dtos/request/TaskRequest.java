package com.tobeto_case.tobeto_case.services.dtos.request;

import com.tobeto_case.tobeto_case.entities.Task;
import com.tobeto_case.tobeto_case.entities.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record TaskRequest(Long projectId,@NotBlank String title,  String description, @NotNull Date createdDate, TaskStatus status) {
    public Task toEntity() {
        return new Task(null,null, this.title(), this.description(), this.createdDate(), this.status());
    }
}

