package org.example.novelypeakapi.task.controller;

import jakarta.validation.Valid;
import org.example.novelypeakapi.task.dto.TaskDTO;
import org.example.novelypeakapi.task.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDTO.ResponseTaskDTO> create(
            @RequestParam UUID userId,
            @Valid @RequestBody TaskDTO.RequestTaskDTO request) {

        TaskDTO.ResponseTaskDTO response = taskService.createTask(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO.ResponseTaskDTO>> getByUser(
            @PathVariable UUID userId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {

        List<TaskDTO.ResponseTaskDTO> response = taskService.getTasksByUserWithFilters(userId, status, priority);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO.ResponseTaskDTO> getById(@PathVariable UUID id) {
        TaskDTO.ResponseTaskDTO response = taskService.getTaskById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO.ResponseTaskDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody TaskDTO.RequestTaskDTO request) {

        TaskDTO.ResponseTaskDTO response = taskService.updateTask(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}