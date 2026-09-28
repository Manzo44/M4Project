// SubtaskController.java
// Cristian Manzo
// September 27, 2026
// REST endpoints for subtasks

package edu.fscj.cen3024c.taskmanager.controllers;

import edu.fscj.cen3024c.taskmanager.dto.SubtaskDTO;
import edu.fscj.cen3024c.taskmanager.entities.Subtask;
import edu.fscj.cen3024c.taskmanager.services.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subtasks")
public class SubtaskController {

    @Autowired
    private SubtaskService subtaskService;

    // GET /subtasks returns every subtask as a DTO
    @GetMapping
    public List<SubtaskDTO> getAllSubtasks() {
        return subtaskService.findAll();
    }

    // GET /subtasks/1 returns one subtask, or a 404 when that id is missing
    @GetMapping("/{id}")
    public SubtaskDTO getSubtaskById(@PathVariable Integer id) {
        return subtaskService.findById(id);
    }

    // POST /subtasks saves a subtask under the task id in the JSON body
    @PostMapping
    public SubtaskDTO createSubtask(@RequestBody Subtask subtask) {
        Subtask savedSubtask = subtaskService.save(subtask);
        return subtaskService.convertToDTO(savedSubtask);
    }

    // PUT /subtasks/1 updates the title and status, the parent task stays the same
    @PutMapping("/{id}")
    public SubtaskDTO updateSubtask(@PathVariable Integer id, @RequestBody Subtask subtask) {
        return subtaskService.updateSubtask(id, subtask);
    }

    // DELETE /subtasks/1 removes the subtask and answers 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Integer id) {
        subtaskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
