package JavaSpring.Builder.dev;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskLeticsController {

    private final TaskLeticsRepository taskRepository;

    public TaskLeticsController(TaskLeticsRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    @GetMapping("/home")
    public String home() {
        return "Welcome to TaskLetics!";
    }

    @GetMapping("")
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task findById(@PathVariable int id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task with ID " + id + " not found.");
        }
        return task.get();
    }

    //post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@RequestBody Task task){
        taskRepository.create(task);
    }
    //put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Task task, @PathVariable Integer id){
        taskRepository.update(task, id);
    }
    //delete
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        taskRepository.delete(id);
    }
}

    /*     @GetMapping("/Tasks")
        public List<Task> getAllTasks() {
            return taskRepository.findAll();
        }
        
        @GetMapping("/about")
        public String about() {
            return "TaskLetics - Your unified task management system for school, work, workouts, and nutrition!";
        }
    */
