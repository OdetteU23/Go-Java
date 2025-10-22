package JavaSpring.Builder.dev;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class DataInitializer {
    
    @Bean
    CommandLineRunner initDatabase(TaskLeticsRepository repository) {
        return args -> {
            // Clearing existing data 
            repository.deleteAll();
            
            // Initializing
            Task task1 = new Task("Learn Go and Java's Spring Boot", 
                "Creating the TaskLetics application using Go or Java Spring Boot", 
                LocalDate.now().plusDays(7), TaskType.SCHOOL);
            task1.setTaskStatus("In Progress");
            
            Task task2 = new Task("Sprint planning meeting", 
                "Attend the team sprint planning session", 
                LocalDate.now().plusDays(2), TaskType.WORK);
            task2.setTaskStatus("Pending");
            
            Task task3 = new Task("Leg day workout", 
                "Complete lower body strength training", 
                LocalDate.now().plusDays(1), TaskType.WORKOUT);
            task3.setTaskStatus("Pending");
            
             Task task4 = new Task("Meal prep day", 
                "Making some protein shakes and healthy meals", 
                LocalDate.now().plusDays(2), TaskType.NUTRITION);
            task3.setTaskStatus("Pending");
            // Saving a task to MongoDB
            repository.save(task1);
            repository.save(task2);
            repository.save(task3);
            repository.save(task4);
            
            System.out.println("Data initialized in MongoDB!");
        };
    }
}
