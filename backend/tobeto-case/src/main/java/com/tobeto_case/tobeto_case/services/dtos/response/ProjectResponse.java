package com.tobeto_case.tobeto_case.services.dtos.response;

import com.tobeto_case.tobeto_case.entities.Project;
import java.util.Date;



public record ProjectResponse(Long id, String projectName, Date startDate, Date endDate) {
    public ProjectResponse(Project project) {
        this(project.getId(), project.getProjectName(), project.getStartDate(), project.getEndDate());
    }
}
