package entities;

import java.time.LocalDateTime;

public class FitnessClassFactory {
    public static FitnessClass createClass(FitnessClassType type, String instructorName, LocalDateTime scheduleTime) {
        return new FitnessClass(
                0,
                type.getDisplayName(),
                instructorName,
                scheduleTime,
                type.getDefaultCapacity()
        );
    }
}