// UserDTO.java
// Cristian Manzo
// September 27, 2026
// User DTO that leaves out the password and lists assigned task IDs

package edu.fscj.cen3024c.taskmanager.dto;

import java.util.Set;

public class UserDTO {

    private Integer id;
    private String username;
    private Set<Integer> taskIds;   // IDs of the tasks this user is assigned to

    // Constructors
    public UserDTO() {}

    public UserDTO(Integer id, String username, Set<Integer> taskIds) {
        this.id = id;
        this.username = username;
        this.taskIds = taskIds;
    }

    // Getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Set<Integer> getTaskIds() { return taskIds; }
    public void setTaskIds(Set<Integer> taskIds) { this.taskIds = taskIds; }
}
