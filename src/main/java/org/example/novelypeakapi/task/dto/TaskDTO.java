package org.example.novelypeakapi.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TaskDTO {

    public record RequestTaskDTO(
            @NotBlank(message = "O título é obrigatório")
            @Size(max = 150)
            String title,
            String description,
            String priority,
            String status,
            List<String> links,
            LocalDateTime dueDate,
            List<RequestSubtaskDTO> subtasks
    ) {}

    public record RequestSubtaskDTO(
            @NotBlank
            @Size(max = 150)
            String title,
            boolean done
    ) {}

    public record ResponseTaskDTO(
            UUID id,
            UUID userId,
            String title,
            String description,
            String priority,
            String status,
            List<String> links,
            LocalDateTime dueDate,
            List<ResponseSubtaskDTO> subtasks
    ) {}

    public record ResponseSubtaskDTO(
            UUID id,
            String title,
            boolean done
    ) {}
}