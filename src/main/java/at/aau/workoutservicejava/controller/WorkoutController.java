package at.aau.workoutservicejava.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.aau.workoutservicejava.dto.WorkoutDto;
import at.aau.workoutservicejava.model.Workout;
import at.aau.workoutservicejava.service.WorkoutService;

@Tag(name = "Workout", description = "Operations related to workout management")
@RestController
@RequestMapping("/workouts")
public class WorkoutController {

  private static final Logger log = LoggerFactory.getLogger(WorkoutController.class);

  private final WorkoutService workoutService;

  public WorkoutController(WorkoutService workoutService) {
    this.workoutService = workoutService;
  }

  @Operation(
      summary = "Retrieve all workouts",
      description = "Fetches a list of all workouts available in the system.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Retrieved all workouts successfully"),
        @ApiResponse(responseCode = "204", description = "No workouts found", content = @Content)
      })
  @GetMapping
  public ResponseEntity<List<Workout>> getAllWorkouts() {
    List<Workout> workouts = workoutService.getAllWorkouts();
    if (workouts.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(workouts);
  }

  @Operation(
      summary = "Create a new workout",
      description = "Adds a new workout to the system based on the provided details.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Workout created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content)
      })
  @PostMapping("/create")
  public ResponseEntity<Workout> createWorkout(
      @RequestBody(description = "Details of the workout to be created", required = true)
          WorkoutDto workoutDto) {
    try {
      Workout workout = workoutService.createWorkout(workoutDto);
      return ResponseEntity.ok(workout);
    } catch (Exception e) {
      log.error("Error creating workout; returning 500 - [workoutDto='{}']", workoutDto, e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @Operation(
      summary = "Retrieve a workout by ID",
      description = "Fetches a workout from the system using its unique identifier.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Workout retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Workout not found", content = @Content),
      })
  @GetMapping("/{workoutId}")
  public ResponseEntity<Workout> getWorkoutById(
      @Parameter(description = "Unique identifier of the workout to retrieve", required = true)
          @PathVariable
          Long workoutId) {
    return workoutService.getWorkoutById(workoutId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Retrieve all workouts for a specific user",
      description = "Fetches a list of all workouts associated with the specified user ID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the list of workouts"),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content),
      })
  @GetMapping("/{userId}")
  public ResponseEntity<List<Workout>> getAllWorkoutsByUserId(
      @Parameter(
              description = "Unique identifier of the user whose workouts are to be retrieved",
              required = true)
          @PathVariable
          Long userId) {
    try {
      List<Workout> workouts = workoutService.getAllWorkoutsByUserId(userId);
      return ResponseEntity.ok(workouts);
    } catch (Exception e) {
      log.error("Error getting workouts by user id; returning 500 - [userId='{}']", userId, e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @Operation(
      summary = "Retrieve the most recent workout for a specific user",
      description = "Fetches the latest workout associated with the specified user ID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the latest workout"),
        @ApiResponse(
            responseCode = "404",
            description = "No workout found for the given user ID",
            content = @Content)
      })
  @GetMapping("/last/{userId}")
  public ResponseEntity<Workout> getLastWorkoutByUserId(
      @Parameter(
              description = "Unique identifier of the user whose latest workout is to be retrieved",
              required = true)
          @PathVariable
          Long userId) {
    return workoutService.getLastWorkoutByUserId(userId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Retrieve a workout by user ID and workout name",
      description =
          "Fetches a specific workout associated with the given user ID and workout name.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Workout retrieved successfully"),
        @ApiResponse(
            responseCode = "404",
            description = "Workout not found for the given user ID and workout name",
            content = @Content)
      })
  @GetMapping("/user/{userID}/{workoutName}")
  public ResponseEntity<Workout> getWorkoutByUserIdAndByWorkoutName(
      @Parameter(description = "Unique identifier of the user", required = true) @PathVariable
          Long userID,
      @Parameter(description = "Name of the workout", required = true) @PathVariable
          String workoutName) {
    return workoutService.getWorkoutByUserIdAndWorkoutName(userID, workoutName)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Delete a workout by ID",
      description = "Removes a workout from the system using its unique identifier.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Workout deleted successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @DeleteMapping("/{workoutId}")
  public ResponseEntity<Void> deleteWorkoutById(
      @Parameter(description = "Unique identifier of the workout to delete", required = true)
          @PathVariable
          Long workoutId) {
    try {
      workoutService.deleteWorkoutById(workoutId);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      log.error("Error deleting workout; returning 500 - [workoutId='{}']", workoutId, e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
