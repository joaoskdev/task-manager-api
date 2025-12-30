package com.sabakeviski.task_manager_api.service;

import com.sabakeviski.task_manager_api.exception.ResourceNotFoundException;
import com.sabakeviski.task_manager_api.model.Task;
import com.sabakeviski.task_manager_api.model.TaskStatus;
import com.sabakeviski.task_manager_api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Task findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    public Task create(Task task) {
        return repository.save(task);
    }

    public Task update(Long id, Task taskDetails) {
        Task task = findById(id);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        return repository.save(task);
    }

    public void delete(Long id) {
        Task task = findById(id);
        repository.delete(task);
    }

    public List<Task> findByStatus(TaskStatus status) {
        return repository.findByStatus(status);
    }
}