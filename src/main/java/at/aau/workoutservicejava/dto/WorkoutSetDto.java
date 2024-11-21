package at.aau.workoutservicejava.dto;

import java.io.Serializable;

/**
 * DTO for {@link at.aau.workoutservicejava.model.WorkoutSet}
 */
public record WorkoutSetDto(Long exerciseID, Integer reps, Double weights) implements Serializable {
  }