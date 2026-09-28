// SubtaskService.java
// Cristian Manzo
// September 27, 2026
// Subtask service with CRUD operations and DTO conversion

package edu.fscj.cen3024c.taskmanager.services;

import edu.fscj.cen3024c.taskmanager.dto.SubtaskDTO;
import edu.fscj.cen3024c.taskmanager.entities.Subtask;
import edu.fscj.cen3024c.taskmanager.entities.Task;
import edu.fscj.cen3024c.taskmanager.enums.SubtaskStatus;
import edu.fscj.cen3024c.taskmanager.exceptions.SubtaskNotFoundException;
import edu.fscj.cen3024c.taskmanager.exceptions.TaskNotFoundException;
import edu.fscj.cen3024c.taskmanager.repositories.SubtaskRepository;
import edu.fscj.cen3024c.taskmanager.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final TaskRepository taskRepository;

    public SubtaskService(SubtaskRepository subtaskRepository, TaskRepository taskRepository) {
        this.subtaskRepository = subtaskRepository;
        this.taskRepository = taskRepository;
    }

    // CRUD Methods with DTO conversion

    // Every subtask, converted to DTOs
    @Transactional(readOnly = true)
    public List<SubtaskDTO> findAll() {
        return subtaskRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // One subtask as a DTO, throwing a 404 exception when the id is not in the table
    @Transactional(readOnly = true)
    public SubtaskDTO findById(Integer id) {
        Subtask subtask = subtaskRepository.findById(id)
                .orElseThrow(() -> new SubtaskNotFoundException(id));
        return convertToDTO(subtask);
    }

    public Subtask save(Subtask subtask) {
        // Default status if not provided
        if (subtask.getStatus() == null) {
            subtask.setStatus(SubtaskStatus.PENDING);
        }
        // Look up the parent task so a bad task ID returns 404 instead of a database error
        if (subtask.getTask() != null && subtask.getTask().getId() != null) {
            Integer taskId = subtask.getTask().getId();
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new TaskNotFoundException(taskId));
            subtask.setTask(task);
        }
        return subtaskRepository.save(subtask);
    }

    // Loads the existing subtask, overwrites the title and status, and saves it back
    @Transactional
    public SubtaskDTO updateSubtask(Integer id, Subtask subtaskDetails) {
        Subtask existingSubtask = subtaskRepository.findById(id)
                .orElseThrow(() -> new SubtaskNotFoundException(id));

        existingSubtask.setTitle(subtaskDetails.getTitle());
        existingSubtask.setStatus(subtaskDetails.getStatus());
        Subtask updatedSubtask = subtaskRepository.save(existingSubtask);

        return convertToDTO(updatedSubtask);
    }

    // Removing a subtask by id, or a 404 when it doesn't exist
    public void deleteById(Integer id) {
        if (!subtaskRepository.existsById(id)) {
            throw new SubtaskNotFoundException(id);
        }
        subtaskRepository.deleteById(id);
    }

    // Entity to DTO Conversion

    public SubtaskDTO convertToDTO(Subtask subtask) {
        Integer taskId = (subtask.getTask() != null) ? subtask.getTask().getId() : null;

        return new SubtaskDTO(
                subtask.getId(),
                subtask.getTitle(),
                subtask.getStatus().name(),
                taskId
        );
    }
}
