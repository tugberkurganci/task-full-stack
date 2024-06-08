package com.tobeto_case.tobeto_case.controller;

import com.tobeto_case.tobeto_case.services.dtos.request.ProjectRequest;
import com.tobeto_case.tobeto_case.services.dtos.response.ProjectResponse;
import com.tobeto_case.tobeto_case.services.abstracts.ProjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {


    private ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectResponse> getAllProjects() {
        return projectService.getAllProjects();
    }


    @GetMapping("/{id}")
    public ProjectResponse getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse createProject(@Valid @RequestBody ProjectRequest projectRequest) {
        return projectService.addProject(projectRequest);
    }


    @PutMapping("/{id}")
    public ProjectResponse updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest projectRequest) {
        return projectService.updateProject(id, projectRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @GetMapping("/page")
    public Page<ProjectResponse> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String similarProjectName,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        return projectService.getAllProjects(page, size, similarProjectName, sortOrder);
    }
}