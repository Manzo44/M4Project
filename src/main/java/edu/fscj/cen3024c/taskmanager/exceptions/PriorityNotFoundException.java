// PriorityNotFoundException.java
// Cristian Manzo
// September 27, 2026
// Exception for handling Priority not found cases (returns 404)

package edu.fscj.cen3024c.taskmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PriorityNotFoundException extends RuntimeException {
    public PriorityNotFoundException(Integer id) {
        super("Priority not found with id " + id);
    }
}
