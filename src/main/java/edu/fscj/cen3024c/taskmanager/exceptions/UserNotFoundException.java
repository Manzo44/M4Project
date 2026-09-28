// UserNotFoundException.java
// Cristian Manzo
// September 27, 2026
// Exception for handling User not found cases (returns 404)

package edu.fscj.cen3024c.taskmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer id) {
        super("User not found with id " + id);
    }
}
