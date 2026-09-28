// PriorityService.java
// Cristian Manzo
// September 27, 2026
// Read-only service for Priority entity

package edu.fscj.cen3024c.taskmanager.services;

import edu.fscj.cen3024c.taskmanager.entities.Priority;
import edu.fscj.cen3024c.taskmanager.exceptions.PriorityNotFoundException;
import edu.fscj.cen3024c.taskmanager.repositories.PriorityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PriorityService {

    private final PriorityRepository priorityRepository;

    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    // Every priority, LOW, MEDIUM, and HIGH
    @Transactional(readOnly = true)
    public List<Priority> findAll() {
        return priorityRepository.findAll();
    }

    // One priority, throwing a 404 exception when the id is not in the table
    @Transactional(readOnly = true)
    public Priority findById(Integer id) {
        return priorityRepository.findById(id)
                .orElseThrow(() -> new PriorityNotFoundException(id));
    }
}
