// UserController.java
// Cristian Manzo
// September 27, 2026
// REST endpoints for users (every response is a DTO, so the password is never returned)

package edu.fscj.cen3024c.taskmanager.controllers;

import edu.fscj.cen3024c.taskmanager.dto.UserDTO;
import edu.fscj.cen3024c.taskmanager.entities.User;
import edu.fscj.cen3024c.taskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GET /users returns every user as a DTO
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    // GET /users/1 returns one user, or a 404 when that id is missing
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    // POST /users saves the new user and sends back the DTO, so the password stays hidden
    @PostMapping
    public UserDTO createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return userService.convertToDTO(savedUser);
    }

    // PUT /users/1 changes the username and password, the response still leaves the password out
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Integer id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // DELETE /users/1 removes the user and answers 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
