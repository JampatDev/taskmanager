package com.jampatdev.taskmanager.controller;

import com.jampatdev.taskmanager.model.Task;
import com.jampatdev.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        String userEmail = getCurrentUserEmail();
        Task created = taskService.createTask(task, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        String userEmail = getCurrentUserEmail();
        List<Task> tasks = taskService.getTasksForUser(userEmail);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        String userEmail = getCurrentUserEmail();
        Task task = taskService.getTaskById(userEmail, id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable Task.Status status) {
        String userEmail = getCurrentUserEmail();
        List<Task> tasks = taskService.getTasksForUserByStatus(userEmail, status);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task updatedTask) {
        String userEmail = getCurrentUserEmail();
        Task updated = taskService.updateTask(userEmail, id, updatedTask);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id) {
        String userEmail = getCurrentUserEmail();
        taskService.deleteTask(userEmail, id);
        return ResponseEntity.noContent().build();
    }

    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
