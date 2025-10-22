package JavaSpring.Builder.dev;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskLeticsRepository extends MongoRepository<Task, String> {
    // - findAll()
    // - findById(String id)
    // - save(Task task)
    // - deleteById(String id)
    // - count()
    // and many more
    
    // Custom query methods:
    // List<Task> findByType(TaskType type);
    // List<Task> findByCompleted(boolean completed);
    // List<Task> findByDueDateBefore(LocalDate date);
}
