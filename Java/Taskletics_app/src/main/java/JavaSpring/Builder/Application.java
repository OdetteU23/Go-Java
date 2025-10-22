

package JavaSpring.Builder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import JavaSpring.Builder.dev.TaskLetics;
import JavaSpring.Builder.dev.Location;

@SuppressWarnings("unused")
@SpringBootApplication
public class Application {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner runner()
	{
		return args -> {
			//log.info;
			TaskLetics dev = new TaskLetics(1, "TaskLetics", LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS), Location.REMOTE);
			log.info("Dev:" + dev + "Application started successfully");
		};
	}

}
