package org.example.novelypeakapi.task.repository;

import org.example.novelypeakapi.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByUserIdOrderByPriorityAsc(UUID userId);

    List<Task> findByUserIdAndStatusAndPriority(UUID userId, Task.Status status, Task.Priority priority);

    List<Task> findByUserIdAndPriority(UUID userId, Task.Priority priority);

    List<Task> findByUserIdAndStatus(UUID userId, Task.Status status);
}