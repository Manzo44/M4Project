// SubtaskNotFoundException.java
// Cristian Manzo
// September 27, 2026
// Exception for handling Subtask not found cases (returns 404)

package edu.fscj.cen3024c.taskmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubtaskNotFoundException extends RuntimeException {
    public SubtaskNotFoundException(Integer id) {
        super("Subtask not found with id " + id);
    }
}
