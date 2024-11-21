package at.aau.workoutservicejava.service;

import at.aau.workoutservicejava.dto.WorkoutDto;
import at.aau.workoutservicejava.dto.WorkoutSetDto;
import at.aau.workoutservicejava.model.Workout;
import at.aau.workoutservicejava.model.WorkoutSet;
import at.aau.workoutservicejava.repository.WorkoutRepository;
import at.aau.workoutservicejava.repository.WorkoutSetRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WorkoutServiceImpl implements WorkoutService {
  private final WorkoutRepository workoutRepository;
  private final WorkoutSetRepository workoutSetRepository;

  public WorkoutServiceImpl(
      WorkoutRepository workoutRepository, WorkoutSetRepository workoutSetRepository) {
    this.workoutRepository = workoutRepository;
    this.workoutSetRepository = workoutSetRepository;
  }

  @Override
  public List<Workout> getAllWorkouts() {
    return workoutRepository.findAll();
  }

  @Override
  public Workout createWorkout(WorkoutDto workoutDto) {
    Workout workout = new Workout();

    workout.setUserID(workoutDto.userID());
    workout.setWorkoutName(workoutDto.workoutName());

    Workout savedWorkout = workoutRepository.save(workout);

    for (WorkoutSetDto workoutSetDto : workoutDto.workoutSets()) {
      WorkoutSet workoutSet = new WorkoutSet();

      workoutSet.setWorkout(savedWorkout);
      workoutSet.setExerciseID(workoutSetDto.exerciseID());
      workoutSet.setReps(workoutSetDto.reps());
      workoutSet.setWeights(workoutSetDto.weights());

      workoutSetRepository.save(workoutSet);
    }

    return savedWorkout;
  }

  @Override
  public Optional<Workout> getWorkoutById(Long workoutId) {
    return workoutRepository.findById(workoutId);
  }

  @Override
  public List<Workout> getAllWorkoutsByUserId(Long userID) {
    return workoutRepository.findAllByUserID(userID);
  }

  @Override
  public Optional<Workout> getLastWorkoutByUserId(Long userID) {
    return workoutRepository.findFirstByUserIDOrderByCreatedDateDesc(userID);
  }

  @Override
  public Optional<Workout> getWorkoutByUserIdAndWorkoutName(Long userID, String workoutName) {
    return workoutRepository.findFirstByUserIDAndWorkoutName(userID, workoutName);
  }

  @Override
  public void deleteWorkoutById(Long workoutID) {
    workoutRepository.deleteById(workoutID);
  }
}
