package JavaSpring.Builder.dev;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Location {
    INDOOR, 
    OUTDOOR, 
    REMOTE, 
    OFFICE;

    @JsonCreator
    public static Location fromString(String value) {
        if (value == null) {
            return null;
        }
        return Location.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
