package at.aau.workoutservicejava.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "Represents the data required to create or update a single set in a workout.")
public record WorkoutSetDto(
    @Schema(
            description = "The unique identifier of the exercise performed in this set.",
            example = "8")
        Long exerciseID,
    @Schema(description = "The number of repetitions performed in this set.", example = "12")
        Integer reps,
    @Schema(description = "The weight (in kilograms) lifted during this set.", example = "50.0")
        Double weights)
    implements Serializable {}
