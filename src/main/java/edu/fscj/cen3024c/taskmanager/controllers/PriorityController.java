// PriorityController.java
// Cristian Manzo
// September 27, 2026
// Controller for Priority entity (read-only)

package edu.fscj.cen3024c.taskmanager.controllers;

import edu.fscj.cen3024c.taskmanager.entities.Priority;
import edu.fscj.cen3024c.taskmanager.services.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/priorities")
public class PriorityController {

    @Autowired
    private PriorityService priorityService;

    // Read only, the three priorities are loaded by data.sql at startup

    // GET /priorities returns LOW, MEDIUM, and HIGH
    @GetMapping
    public List<Priority> getAllPriorities() {
        return priorityService.findAll();
    }

    // GET /priorities/1 returns one priority, or a 404 when that id is missing
    @GetMapping("/{id}")
    public Priority getPriorityById(@PathVariable Integer id) {
        return priorityService.findById(id);
    }
}
