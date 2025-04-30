package com.jampatdev.taskmanager.service;

import com.jampatdev.taskmanager.model.Task;
import com.jampatdev.taskmanager.model.User;
import com.jampatdev.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public Task createTask(Task task, String userEmail) {
        User user = userService.getUserByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userEmail));
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getTasksForUser(String userEmail) {
        User user = userService.getUserByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userEmail));
        return taskRepository.findByUserId(user.getId());
    }

    public List<Task> getTasksForUserByStatus(String userEmail, Task.Status status) {
        User user = userService.getUserByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userEmail));
        return taskRepository.findByUserIdAndStatus(user.getId(), status);
    }

    public Task getTaskById(String userEmail, Long taskId) {
        User user = userService.getUserByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userEmail));
        return taskRepository.findById(taskId)
                .filter(task -> task.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new IllegalArgumentException("Task not found or not owned by user"));
    }

    public Task updateTask(String userEmail, Long taskId, Task updated) {
        Task existing = getTaskById(userEmail, taskId);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setDueDate(updated.getDueDate());
        existing.setStatus(updated.getStatus());
        return taskRepository.save(existing);
    }

    public void deleteTask(String userEmail, Long taskID) {
        Task existing = getTaskById(userEmail, taskID);
        taskRepository.delete(existing);
    }
}
