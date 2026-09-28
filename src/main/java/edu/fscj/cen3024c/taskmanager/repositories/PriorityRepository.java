// PriorityRepository.java
// Cristian Manzo
// September 27, 2026
// Repository for Priority entity

package edu.fscj.cen3024c.taskmanager.repositories;

import edu.fscj.cen3024c.taskmanager.entities.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Extending JpaRepository hands us findAll, findById, save, and deleteById for priorities with no code written
@Repository
public interface PriorityRepository extends JpaRepository<Priority, Integer> {
}
