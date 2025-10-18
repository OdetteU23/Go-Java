package JavaSpring.Builder.dev;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskType {
    SCHOOL,
    WORK,
    WORKOUT,
    NUTRITION,
    CALENDAR_EVENT;

    @JsonCreator
    public static TaskType fromString(String value) {
        if (value == null) {
            return null;
        }
        return TaskType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}