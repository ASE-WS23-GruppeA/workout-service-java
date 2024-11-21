package at.aau.workoutservicejava.service;

import at.aau.workoutservicejava.dto.WorkoutDto;
import at.aau.workoutservicejava.model.Workout;
import java.util.List;
import java.util.Optional;

public interface WorkoutService {

  List<Workout> getAllWorkouts();

  Workout createWorkout(WorkoutDto workoutDto);

  Optional<Workout> getWorkoutById(Long workoutId);

  List<Workout> getAllWorkoutsByUserId(Long userID);

  Optional<Workout> getLastWorkoutByUserId(Long userID);

  Optional<Workout> getWorkoutByUserIdAndWorkoutName(Long userID, String workoutName);

  void deleteWorkoutById(Long workoutID);
}
