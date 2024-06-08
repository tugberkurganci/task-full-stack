package com.tobeto_case.tobeto_case.services.abstracts;
import com.tobeto_case.tobeto_case.services.dtos.request.TaskRequest;
import com.tobeto_case.tobeto_case.services.dtos.response.TaskResponse;

import java.util.List;

public interface TaskService {
    List<TaskResponse> getAllTasks();
    TaskResponse getTaskById(Long id);
    TaskResponse addTask(TaskRequest taskRequest);
    TaskResponse updateTask(Long id, TaskRequest taskRequest);
    void deleteTask(Long id);
    List<TaskResponse> getTasksByProjectId(Long id);

    List<TaskResponse> sortTasksByStatus(String status,Long projectId);
}
