// UserService.java
// Cristian Manzo
// September 27, 2026
// User service with CRUD operations and DTO conversion (password never leaves the service)

package edu.fscj.cen3024c.taskmanager.services;

import edu.fscj.cen3024c.taskmanager.dto.UserDTO;
import edu.fscj.cen3024c.taskmanager.entities.Task;
import edu.fscj.cen3024c.taskmanager.entities.User;
import edu.fscj.cen3024c.taskmanager.exceptions.UserNotFoundException;
import edu.fscj.cen3024c.taskmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CRUD Methods with DTO conversion

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return convertToDTO(user);
    }

    // Helper to return raw entity (for updates, deletes, and task assignment)
    @Transactional(readOnly = true)
    public User findByIdEntity(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public UserDTO updateUser(Integer id, User userDetails) {
        User existingUser = findByIdEntity(id);
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setPassword(userDetails.getPassword()); // will be hashed in a later module
        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    @Transactional
    public void deleteById(Integer id) {
        User user = findByIdEntity(id);
        // Task owns the user_tasks join table, so unlink the user from each task before deleting
        if (user.getTasks() != null) {
            for (Task task : user.getTasks()) {
                task.getUsers().remove(user);
            }
        }
        userRepository.delete(user);
    }

    // Entity to DTO Conversion

    public UserDTO convertToDTO(User user) {
        Set<Integer> taskIds = (user.getTasks() != null)
                ? user.getTasks().stream()
                .map(Task::getId)
                .collect(Collectors.toSet())
                : Set.of();

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                taskIds
        );
    }
}
