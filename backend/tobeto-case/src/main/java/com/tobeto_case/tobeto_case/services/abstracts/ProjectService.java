package com.tobeto_case.tobeto_case.services.abstracts;

import com.tobeto_case.tobeto_case.entities.Project;
import com.tobeto_case.tobeto_case.services.dtos.request.ProjectRequest;
import com.tobeto_case.tobeto_case.services.dtos.response.ProjectResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> getAllProjects();

    ProjectResponse getProjectById(Long id);

    ProjectResponse addProject(ProjectRequest projectRequest);

    ProjectResponse updateProject(Long id, ProjectRequest projectRequest);

    void deleteProject(Long id);

    Project getProjectEntityById(Long id);

    Page<ProjectResponse> getAllProjects(int page, int size, String similarProjectName, String sortOrder);
}