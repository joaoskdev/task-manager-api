package com.sabakeviski.task_manager_api.repository;

import com.sabakeviski.task_manager_api.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
}