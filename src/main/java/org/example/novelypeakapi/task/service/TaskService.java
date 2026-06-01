package org.example.novelypeakapi.task.service;

import org.example.novelypeakapi.exception.ResourceNotFoundException;
import org.example.novelypeakapi.task.dto.TaskDTO;
import org.example.novelypeakapi.task.mapper.TaskMapper;
import org.example.novelypeakapi.task.model.Task;
import org.example.novelypeakapi.task.repository.TaskRepository;
import org.example.novelypeakapi.user.model.User;
import org.example.novelypeakapi.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional
    public TaskDTO.ResponseTaskDTO createTask(UUID userId, TaskDTO.RequestTaskDTO requestTaskDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        Task task = taskMapper.toEntity(requestTaskDTO);
        task.setUser(user);

        Task taskSaved = taskRepository.save(task);
        return taskMapper.toResponse(taskSaved);
    }

    @Transactional(readOnly = true)
    public List<TaskDTO.ResponseTaskDTO> getTasksByUserWithFilters(UUID userId, String status, String priority) {
        List<Task> tasks;

        if (status != null && priority != null) {
            tasks = taskRepository.findByUserIdAndStatusAndPriority(userId, Task.Status.valueOf(status), Task.Priority.valueOf(priority));
        } else if (status != null) {
            tasks = taskRepository.findByUserIdAndStatus(userId, Task.Status.valueOf(status));
        } else if (priority != null) {
            tasks = taskRepository.findByUserIdAndPriority(userId, Task.Priority.valueOf(priority));
        } else {
            tasks = taskRepository.findByUserIdOrderByPriorityAsc(userId);
        }

        return taskMapper.toResponseList(tasks);
    }

    @Transactional(readOnly = true)
    public TaskDTO.ResponseTaskDTO getTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o ID: " + id));
        return taskMapper.toResponse(task);
    }

    @Transactional
    public TaskDTO.ResponseTaskDTO updateTask(UUID id, TaskDTO.RequestTaskDTO requestDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o ID: " + id));

        task.setTitle(requestDTO.title());
        task.setDescription(requestDTO.description());
        task.setPriority(Task.Priority.valueOf(requestDTO.priority()));
        task.setStatus(Task.Status.valueOf(requestDTO.status()));
        task.setDueDate(requestDTO.dueDate());

        task.getSubtasks().clear();
        if (requestDTO.subtasks() != null) {
            requestDTO.subtasks().forEach(subtaskDto -> {
                task.addSubtask(taskMapper.subtaskRequestToEntity(subtaskDto));
            });
        }

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toResponse(updatedTask);
    }

    @Transactional
    public void deleteTask(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o ID: " + id));
        taskRepository.delete(task);
    }
}