package org.example.novelypeakapi.task.mapper;

import org.example.novelypeakapi.task.dto.TaskDTO;
import org.example.novelypeakapi.task.model.Subtask;
import org.example.novelypeakapi.task.model.Task;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "subtasks", ignore = true)
    Task toEntity(TaskDTO.RequestTaskDTO requestTaskDTO);

    @Mapping(source = "user.id", target = "userId")
    TaskDTO.ResponseTaskDTO toResponse(Task task);

    List<TaskDTO.ResponseTaskDTO> toResponseList(List<Task> tasks);

    Subtask subtaskRequestToEntity(TaskDTO.RequestSubtaskDTO dto);
    
    TaskDTO.ResponseSubtaskDTO subtaskToResponse(Subtask subtask);

    @AfterMapping
    default void establishBidirectionalRelation(TaskDTO.RequestTaskDTO request, @MappingTarget Task task) {
        if (request.subtasks() != null) {
            request.subtasks().forEach(subtaskDto -> {
                Subtask subtask = subtaskRequestToEntity(subtaskDto);
                task.addSubtask(subtask);
            });
        }
    }
}