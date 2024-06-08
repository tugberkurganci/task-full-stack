package com.tobeto_case.tobeto_case.services.dtos.request;

import com.tobeto_case.tobeto_case.entities.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;



public record ProjectRequest(@NotBlank String projectName, @NotNull Date startDate, @NotNull Date endDate) {

    public Project toEntity() {
        return new Project(null, this.projectName(), this.startDate(), this.endDate(),null);
    }
}
