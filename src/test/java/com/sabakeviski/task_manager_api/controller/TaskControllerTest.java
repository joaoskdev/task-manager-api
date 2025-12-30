package com.sabakeviski.task_manager_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabakeviski.task_manager_api.model.Task;
import com.sabakeviski.task_manager_api.model.TaskStatus;
import com.sabakeviski.task_manager_api.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void shouldCreateTask() throws Exception {
        Task task = new Task();
        task.setTitle("Nova Tarefa");
        task.setDescription("Descrição da tarefa");
        task.setStatus(TaskStatus.PENDING);

        ResultActions response = mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.status", is(task.getStatus().name())));
    }

    @Test
    void shouldGetAllTasks() throws Exception {
        Task task1 = new Task();
        task1.setTitle("Tarefa 1");
        task1.setStatus(TaskStatus.PENDING);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Tarefa 2");
        task2.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task2);

        mockMvc.perform(get("/api/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        Task task = new Task();
        task.setTitle("Tarefa Teste");
        task.setDescription("Descrição");
        task.setStatus(TaskStatus.PENDING);
        Task savedTask = taskRepository.save(task);

        mockMvc.perform(get("/api/tasks/{id}", savedTask.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.id", is(savedTask.getId().intValue())));
    }

    @Test
    void shouldGetTasksByStatus() throws Exception {
        Task task1 = new Task();
        task1.setTitle("Tarefa Pendente");
        task1.setStatus(TaskStatus.PENDING);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Tarefa Completa");
        task2.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task2);

        mockMvc.perform(get("/api/tasks?status=PENDING"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].status", is("PENDING")));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        Task task = new Task();
        task.setTitle("Tarefa Original");
        task.setStatus(TaskStatus.PENDING);
        Task savedTask = taskRepository.save(task);

        Task updatedTask = new Task();
        updatedTask.setTitle("Tarefa Atualizada");
        updatedTask.setDescription("Nova descrição");
        updatedTask.setStatus(TaskStatus.COMPLETED);

        mockMvc.perform(put("/api/tasks/{id}", savedTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTask)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(updatedTask.getTitle())))
                .andExpect(jsonPath("$.status", is(updatedTask.getStatus().name())));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        Task task = new Task();
        task.setTitle("Tarefa para Deletar");
        task.setStatus(TaskStatus.PENDING);
        Task savedTask = taskRepository.save(task);

        mockMvc.perform(delete("/api/tasks/{id}", savedTask.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/tasks/{id}", savedTask.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/tasks/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldValidateRequiredFields() throws Exception {
        Task task = new Task();
        // Sem título (campo obrigatório)

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}

