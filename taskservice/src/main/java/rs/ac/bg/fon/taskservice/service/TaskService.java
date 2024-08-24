package rs.ac.bg.fon.taskservice.service;

import rs.ac.bg.fon.taskservice.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAll();
    Task findById(Long id);
    List<Task> findByUserId(Long userId);
    Task findByTitle(String title);
    Task update(Task task, Long id);
    void delete(Long id);
    Task save(Task task);
}
