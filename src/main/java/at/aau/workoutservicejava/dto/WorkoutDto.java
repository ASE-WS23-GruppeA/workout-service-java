package at.aau.workoutservicejava.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;

@Schema(
    description =
        "Represents the data required to create or update a workout session, including its sets.")
public record WorkoutDto(
    @Schema(description = "The ID of the user who owns this workout.", examples = "100") Long userID,
    @Schema(
            description =
                "The name of the workout session, typically describing its focus (e.g., 'Arm Day').",
            examples = "Arm Day")
        String workoutName,
    @Schema(description = "A list of sets included in this workout.")
        List<WorkoutSetDto> workoutSets)
    implements Serializable {}
