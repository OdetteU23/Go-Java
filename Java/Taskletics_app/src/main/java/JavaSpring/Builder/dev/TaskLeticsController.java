package JavaSpring.Builder.dev;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/tasks")
public class TaskLeticsController {

    private final TaskLeticsRepository taskRepository;
    private final MongoTemplate mongoTemplate;

    public TaskLeticsController(TaskLeticsRepository taskRepository, MongoTemplate mongoTemplate) {
        this.taskRepository = taskRepository;
        this.mongoTemplate = mongoTemplate;
    }
    
    @GetMapping("/home")
    public String home() {
        return "Welcome to TaskLetics!";
    }
    //get:
    @GetMapping("")
    public Page<Task> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "dueDate,asc") String sort,
            @RequestParam(name = "type", required = false) TaskType type,
            @RequestParam(name = "completed", required = false) Boolean completed,
            @RequestParam(name = "taskStatus", required = false) String taskStatus,
            @RequestParam(name = "name", required = false) String name
    ) {
        // Sorting out:
        String[] sortParts = sort.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParts.length > 1 ? sortParts[1] : "asc");
        String sortBy = sortParts.length > 0 ? sortParts[0] : "dueDate";
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Building query criteria:
        Query query = new Query();
        if (type != null) {
            query.addCriteria(Criteria.where("type").is(type));
        }
        if (completed != null) {
            query.addCriteria(Criteria.where("completed").is(completed));
        }
        if (taskStatus != null && !taskStatus.isBlank()) {
            query.addCriteria(Criteria.where("taskStatus").regex("^" + taskStatus + "$", "i"));
        }
        if (name != null && !name.isBlank()) {
            query.addCriteria(Criteria.where("name").regex(".*" + name + ".*", "i"));
        }

        boolean hasFilters = (type != null) || (completed != null) || (taskStatus != null && !taskStatus.isBlank()) || (name != null && !name.isBlank());

        if (!hasFilters) {
            return taskRepository.findAll(pageable);
        }

        long total = mongoTemplate.count(query, Task.class);
        query.with(pageable);
        List<Task> tasks = mongoTemplate.find(query, Task.class);
        return new PageImpl<>(tasks, pageable, total);
    }
    //get by id:
    @GetMapping("/{id}")
    public Task findById(@PathVariable String id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task with ID " + id + " not found.");
        }
        return task.get();
    }

    //post
    @PostMapping("")
    public ResponseEntity<Task> create(@Valid @RequestBody Task task){
        Task saved = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    //put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Task task, @PathVariable String id){
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task with ID " + id + " not found.");
        }
        task.setId(id);
        taskRepository.save(task);
    }
    //delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable String id){
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task with ID " + id + " not found.");
        }
        taskRepository.deleteById(id);
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
