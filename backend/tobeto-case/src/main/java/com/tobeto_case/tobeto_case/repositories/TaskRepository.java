package com.tobeto_case.tobeto_case.repositories;

import com.tobeto_case.tobeto_case.entities.Task;
import com.tobeto_case.tobeto_case.entities.TaskStatus;
import com.tobeto_case.tobeto_case.services.dtos.response.TaskResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByProjectId(Long id);

    @Query("SELECT new com.tobeto_case.tobeto_case.services.dtos.response.TaskResponse(t.id, t.title, t.description, t.createdDate, t.status) FROM Task t WHERE t.status = :status AND t.project.id = :projectId ORDER BY t.createdDate DESC")
    List<TaskResponse> sortTasksByStatus(@Param("status") TaskStatus status, @Param("projectId") Long projectId);

}
