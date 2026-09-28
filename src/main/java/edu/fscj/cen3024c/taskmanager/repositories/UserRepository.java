// UserRepository.java
// Cristian Manzo
// September 27, 2026
// Repository for User entity

package edu.fscj.cen3024c.taskmanager.repositories;

import edu.fscj.cen3024c.taskmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Extending JpaRepository hands us findAll, findById, save, and deleteById for users with no code written
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
