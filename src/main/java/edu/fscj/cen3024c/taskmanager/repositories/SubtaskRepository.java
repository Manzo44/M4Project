// SubtaskRepository.java
// Cristian Manzo
// September 27, 2026
// Repository for Subtask entity

package edu.fscj.cen3024c.taskmanager.repositories;

import edu.fscj.cen3024c.taskmanager.entities.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Extending JpaRepository hands us findAll, findById, save, and deleteById for subtasks with no code written
@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, Integer> {
}
