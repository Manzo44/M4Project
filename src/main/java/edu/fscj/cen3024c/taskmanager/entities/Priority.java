// Priority.java
// Cristian Manzo
// September 27, 2026
// Entity for the fixed task priority levels (LOW, MEDIUM, HIGH)

package edu.fscj.cen3024c.taskmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.fscj.cen3024c.taskmanager.enums.PriorityLevel;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "priorities")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PriorityLevel level;

    // One priority can be shared by many tasks, @JsonIgnore keeps this list out of the JSON so it doesn't loop forever
    @OneToMany(mappedBy = "priority", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Task> tasks;

    // Constructors
    public Priority() {}

    public Priority(PriorityLevel level) {
        this.level = level;
    }

    // Getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public PriorityLevel getLevel() { return level; }
    public void setLevel(PriorityLevel level) { this.level = level; }

    public Set<Task> getTasks() { return tasks; }
    public void setTasks(Set<Task> tasks) { this.tasks = tasks; }
}
