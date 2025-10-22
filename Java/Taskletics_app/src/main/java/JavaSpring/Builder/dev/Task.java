package JavaSpring.Builder.dev;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    @NotBlank(message = "name must not be blank")
    private String name;
    private String description;
    private String taskStatus;
    @NotNull(message = "dueDate must not be null")
    private LocalDate dueDate;
    private boolean completed;
    @NotNull(message = "type must not be null")
    private TaskType type;
    private Location location; 
    
    public Task() {}
    
    
    /**
     * @param id
     * @param name
     * @param description
     * @param dueDate
     * @param completed
     * @param type
     * @param taskStatus
     * @param location
     */
    public Task(String id, String name, String description, LocalDate dueDate, boolean completed, TaskType type, String taskStatus, Location location) {
        this.id = id;
        this.name = name;
        this.taskStatus = taskStatus;
        this.description = description;
        this.dueDate = dueDate;
        this.location = location;
        this.completed = completed;
        this.type = type;
    }
    
    // Constructor without ID for new tasks:
    public Task(String name, String description, LocalDate dueDate, TaskType type) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
        this.type = type;
        this.taskStatus = "Pending"; 
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTaskStatus() { return taskStatus; }
    public void setTaskStatus(String taskStatus) { this.taskStatus = taskStatus; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    
    public TaskType getType() { return type; }
    public void setType(TaskType type) { this.type = type; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", completed=" + completed +
                ", type=" + type +
                '}';
    }
}