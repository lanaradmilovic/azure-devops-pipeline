package rs.ac.bg.fon.taskservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.taskservice.exception.TaskNotFoundException;
import rs.ac.bg.fon.taskservice.model.Task;
import rs.ac.bg.fon.taskservice.model.User;
import rs.ac.bg.fon.taskservice.proxy.UserServiceClient;
import rs.ac.bg.fon.taskservice.repository.TaskRepository;
import rs.ac.bg.fon.taskservice.service.TaskService;

import java.util.List;
import java.util.Optional;
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserServiceClient userServiceClient;
    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(Long id) {
        Optional<Task> result = taskRepository.findById(id);
        if (result.isEmpty()) throw new TaskNotFoundException("Task with id = "+id+" does not exist.");
        if (result.get() != null) {
            System.out.println("Start Time: " + result.get().getStartTime());
            System.out.println("End Time: " + result.get().getEndTime());
        }
        return result.get();
    }

    @Override
    public List<Task> findByUserId(Long userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks;
    }

    @Override
    public Task findByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    @Override
    public Task update(Task updatedTask, Long id) {
        if (taskRepository.existsById(id)) {
            updatedTask.setId(id);
            return taskRepository.save(updatedTask);
        }
        throw new TaskNotFoundException("Task with id = "+id+" does not exist.");
    }

    @Override
    public void delete(Long id) {
        findById(id);
        taskRepository.deleteById(id);
    }

    @Override
    public Task save(Task task) {
        ResponseEntity<User> userResponse = userServiceClient.getUserById(task.getUserId());
        User user = userResponse.getBody();
        if (user == null) {
            throw new EntityNotFoundException("User not found with id = " + task.getUserId());
        }

        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setLocation(task.getLocation());
        newTask.setStartTime(task.getStartTime());
        newTask.setEndTime(task.getEndTime());
        newTask.setStatus(task.getStatus());
        newTask.setUserId(user.getId());

        return taskRepository.save(newTask);
    }


}
