package com.tobeto_case.tobeto_case.services.concretes;
import com.tobeto_case.tobeto_case.core.exception.ProjectNotFoundException;
import com.tobeto_case.tobeto_case.entities.Project;
import com.tobeto_case.tobeto_case.entities.Task;
import com.tobeto_case.tobeto_case.core.exception.TaskNotFoundException;
import com.tobeto_case.tobeto_case.entities.TaskStatus;
import com.tobeto_case.tobeto_case.repositories.TaskRepository;
import com.tobeto_case.tobeto_case.services.abstracts.ProjectService;
import com.tobeto_case.tobeto_case.services.abstracts.TaskService;
import com.tobeto_case.tobeto_case.services.dtos.request.TaskRequest;
import com.tobeto_case.tobeto_case.services.dtos.response.TaskResponse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskManager implements TaskService {

    private  TaskRepository taskRepository;
    private ProjectService projectService;


    public TaskManager(TaskRepository taskRepository,ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService=projectService;
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow( ()-> new TaskNotFoundException("Task not found with id: " + id));

        return new TaskResponse(task);
    }

    @Override
    public  List<TaskResponse> getTasksByProjectId(Long id) {
        List<Task> tasks = taskRepository.findByProjectId(id);

        return tasks.stream().map(TaskResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> sortTasksByStatus(String status,Long projectId) {
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        return taskRepository.sortTasksByStatus(taskStatus,projectId);
    }

    @Override
    public TaskResponse addTask(TaskRequest taskRequest) {

        Task task = taskRequest.toEntity();
        Project project=projectService.getProjectEntityById(taskRequest.projectId());
        checkTaskDateBeforeProjectEndTime(taskRequest.createdDate(),project.getEndDate());
        task.setProject(project);
        task.setStatus(TaskStatus.NEW);
        task = taskRepository.save(task);
        return new TaskResponse(task);
    }


        private void checkTaskDateBeforeProjectEndTime(Date taskDate, Date projectEndTime) {
            if (taskDate.after(projectEndTime)) {
                throw new RuntimeException("Error: Task date is after project end time.");
            }

    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));


        if (taskRequest.title() != null) {
            task.setTitle(taskRequest.title());
        }


        if (taskRequest.description() != null) {
            task.setDescription(taskRequest.description());
        }


        if (taskRequest.createdDate() != null) {
            task.setCreatedDate(taskRequest.createdDate());
        }


        if (taskRequest.status() != null) {
            task.setStatus(taskRequest.status());
        }

        task = taskRepository.save(task);
        return new TaskResponse(task);
    }


    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow( ()-> new TaskNotFoundException("Task not found with id: " + id));

        taskRepository.delete(task);
    }

}

