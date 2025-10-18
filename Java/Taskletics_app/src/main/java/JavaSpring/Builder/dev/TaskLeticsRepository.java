package JavaSpring.Builder.dev;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Component
@SuppressWarnings("unused")
@Repository
public class TaskLeticsRepository {
    // This class will handle data storage and retrieval for Task entities
    private List<Task> tasks = new ArrayList<>();
    
    List<Task> findAll() {
        return tasks;
    }

    Optional<Task> findById(int id) {
        return tasks.stream()
                    .filter(task -> task.getId() == id)
                    .findFirst();
    }
    void create(Task task){
        tasks.add(task);
    }

    void update(Task task, Integer id){
        Optional<Task>existingTask = findById(id);
        if(existingTask.isPresent()){
            tasks.set(tasks.indexOf(existingTask.get()), task);
        }
    }
    void delete(Integer id) {
        tasks.removeIf(task -> task.getId() == id);
    }
    
    
    @PostConstruct
    private void initData() {
        // Initialize with some sample data using the unified Task class
        Task task1 = new Task("Learn Go and Java's Spring Boot", "Creating the TaskLetics application using Go or Java Spring Boot", 
                          LocalDate.now().plusDays(7), TaskType.SCHOOL);
        task1.setId(1);
        tasks.add(task1);
        
        Task task2 = new Task("Sprint planning meeting", "Attend the team sprint planning session", 
                          LocalDate.now().plusDays(2), TaskType.WORK);
        task2.setId(2);
        tasks.add(task2);
        
        Task task3 = new Task("Leg day workout", "Complete lower body strength training", 
                          LocalDate.now().plusDays(1), TaskType.WORKOUT);
        task3.setId(3);
        tasks.add(task3);
    }
    
    // Method to add a task
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Method to retrieve all tasks
    public List<Task> getAllTasks() {
        return tasks;
    }

    // Method to find a task by ID
   /*  public Task findById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
    

    // Method to delete a task by ID
    public boolean deleteById(int id) {
        Optional<Task> task = findById(id);
        if (task.isPresent()) {
            tasks.remove(task);
            return true;
        }
        return false;
    }
    */
}
