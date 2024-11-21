package at.aau.workoutservicejava.dto;

import java.io.Serializable;
import java.util.List;

/** DTO for {@link at.aau.workoutservicejava.model.Workout} */
public record WorkoutDto(Long userID, String workoutName, List<WorkoutSetDto> workoutSets)
    implements Serializable {}
