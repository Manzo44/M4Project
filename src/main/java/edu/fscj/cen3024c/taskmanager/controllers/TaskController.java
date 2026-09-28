// TaskController.java
// Cristian Manzo
// September 27, 2026
// REST endpoints for tasks, including their priority and assigned users

package edu.fscj.cen3024c.taskmanager.controllers;

import edu.fscj.cen3024c.taskmanager.dto.TaskDTO;
import edu.fscj.cen3024c.taskmanager.entities.Priority;
import edu.fscj.cen3024c.taskmanager.entities.Task;
import edu.fscj.cen3024c.taskmanager.entities.User;
import edu.fscj.cen3024c.taskmanager.exceptions.PriorityNotFoundException;
import edu.fscj.cen3024c.taskmanager.repositories.PriorityRepository;
import edu.fscj.cen3024c.taskmanager.services.TaskService;
import edu.fscj.cen3024c.taskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    private UserService userService;

    // READ endpoints return DTOs

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Integer id) {
        return taskService.findById(id);
    }

    // WRITE endpoints accept entities but return DTOs

    @PostMapping
    public TaskDTO createTask(@RequestBody Task task) {
        if (task.getPriority() != null && task.getPriority().getId() != null) {
            Priority priority = priorityRepository.findById(task.getPriority().getId())
                    .orElseThrow(() -> new PriorityNotFoundException(task.getPriority().getId()));
            task.setPriority(priority);
        }
        // Swap each {"id": n} in "users" for the real user so the task is linked in user_tasks
        if (task.getUsers() != null) {
            Set<User> users = task.getUsers().stream()
                    .map(user -> userService.findByIdEntity(user.getId()))
                    .collect(Collectors.toSet());
            task.setUsers(users);
        }
        Task savedTask = taskService.save(task);
        return taskService.convertToDTO(savedTask);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Integer id, @RequestBody Task task) {
        if (task.getPriority() != null && task.getPriority().getId() != null) {
            Priority priority = priorityRepository.findById(task.getPriority().getId())
                    .orElseThrow(() -> new PriorityNotFoundException(task.getPriority().getId()));
            task.setPriority(priority);
        }
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
