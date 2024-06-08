package com.tobeto_case.tobeto_case.controller;

import com.tobeto_case.tobeto_case.services.abstracts.TaskService;
import com.tobeto_case.tobeto_case.services.dtos.request.TaskRequest;
import com.tobeto_case.tobeto_case.services.dtos.response.TaskResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private  TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/sort/{status}/{projectId}")
    public List<TaskResponse> sortTasksByStatus(@PathVariable String status,@PathVariable Long projectId) {
        return taskService.sortTasksByStatus(status,projectId);
    }
    @GetMapping("/project-tasks/{id}")
    public List<TaskResponse> getTasksByProjectId(@PathVariable Long id) {
        return taskService.getTasksByProjectId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@Valid @RequestBody TaskRequest taskRequest) {
        return taskService.addTask(taskRequest);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest taskRequest) {
        return taskService.updateTask(id, taskRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
