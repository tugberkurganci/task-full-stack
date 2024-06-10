package com.tobeto_case.tobeto_case.services.concretes;

import static org.junit.jupiter.api.Assertions.*;


import com.tobeto_case.tobeto_case.entities.Project;
import com.tobeto_case.tobeto_case.core.exception.ProjectNotFoundException;
import com.tobeto_case.tobeto_case.repositories.ProjectRepository;
import com.tobeto_case.tobeto_case.services.dtos.request.ProjectRequest;
import com.tobeto_case.tobeto_case.services.dtos.response.ProjectResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectManagerTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectManager projectManager;

    @BeforeEach
    void setUp() {
        projectManager = new ProjectManager(projectRepository);
    }

    @Test
    void getProjectById_ExistingProject_ReturnsProjectResponse() {
        // Test verisi
        Project project = new Project(1L, "Test Project", null, null,null);

        // Mock repository behavior
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        // Testi gerçekleştir
        ProjectResponse result = projectManager.getProjectById(1L);

        // Sonuçları kontrol et
        assertNotNull(result);
        assertEquals("Test Project", result.projectName());
    }

    @Test
    void getProjectById_NonExistingProject_ThrowsProjectNotFoundException() {
        // Mock repository behavior
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // Testi gerçekleştir ve beklenen istisnayı kontrol et
        assertThrows(ProjectNotFoundException.class, () -> projectManager.getProjectById(1L));
    }

    @Test
    void addProject() {
        // Test verisi
        ProjectRequest projectRequest = new ProjectRequest("Test Project",null,null);


        // Mock repository behavior
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> {
            Project project = invocation.getArgument(0);
            project.setId(1L); // Yeni bir proje olduğunu varsayalım, ve id veritabanı tarafından atanmış olsun.
            return project;
        });

        // Testi gerçekleştir
        ProjectResponse result = projectManager.addProject(projectRequest);

        // Sonuçları kontrol et
        assertNotNull(result);
        assertEquals("Test Project", result.projectName());
        assertNotNull(result.id());
    }
}

