// User.java
// Cristian Manzo
// September 27, 2026
// Entity for a system user who can be assigned to tasks

package edu.fscj.cen3024c.taskmanager.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users") // avoid 'user', a reserved word in PostgreSQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // will be hashed in a later module

    // Inverse side of the many-to-many; Task owns the user_tasks join table
    @ManyToMany(mappedBy = "users")
    private Set<Task> tasks;

    // Constructors
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<Task> getTasks() { return tasks; }
    public void setTasks(Set<Task> tasks) { this.tasks = tasks; }
}
