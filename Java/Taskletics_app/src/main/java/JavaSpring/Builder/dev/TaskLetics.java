package JavaSpring.Builder.dev;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;

public record TaskLetics(
    Integer id, 
    @NotEmpty
    String appName, 
    LocalDateTime startedOn, 
    LocalDateTime completedOn,
    @Positive 
    Location location
) {
    public TaskLetics {
        if(!completedOn.isAfter(startedOn)) {
            throw new IllegalArgumentException("completedOn must be after startedOn");
        }
    }
}
