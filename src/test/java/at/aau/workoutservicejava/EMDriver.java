package at.aau.workoutservicejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.evomaster.client.java.controller.EmbeddedSutController;
import org.evomaster.client.java.controller.InstrumentedSutStarter;
import org.evomaster.client.java.controller.api.dto.SutInfoDto;
import org.evomaster.client.java.controller.api.dto.auth.AuthenticationDto;
import org.evomaster.client.java.controller.api.dto.database.schema.DatabaseType;
import org.evomaster.client.java.controller.internal.SutController;
import org.evomaster.client.java.controller.problem.ProblemInfo;
import org.evomaster.client.java.controller.problem.RestProblem;
import org.evomaster.client.java.sql.DbCleaner;
import org.evomaster.client.java.sql.DbSpecification;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class EMDriver extends EmbeddedSutController {

  public static void main(String[] args) {
    SutController controller = new EMDriver();
    InstrumentedSutStarter starter = new InstrumentedSutStarter(controller);

    starter.start();
  }

  private ConfigurableApplicationContext context;
  private Connection connection;

  @Override
  public boolean isSutRunning() {
    return context != null && context.isRunning();
  }

  @Override
  public String getPackagePrefixesToCover() {
    return "at.aau.workoutservicejava";
  }

  @Override
  public List<AuthenticationDto> getInfoForAuthentication() {
    return List.of();
  }

  @Override
  public ProblemInfo getProblemInfo() {
    return new RestProblem("http://localhost:8080/v3/api-docs", Collections.emptyList());
  }

  @Override
  public SutInfoDto.OutputFormat getPreferredOutputFormat() {
    return SutInfoDto.OutputFormat.JAVA_JUNIT_5;
  }

  @Override
  public String startSut() {
    context = SpringApplication.run(WorkoutServiceJavaApplication.class);

    try {
      connection =
          DriverManager.getConnection(
              "jdbc:postgresql://localhost:5432/wt_workout_service", "wt", "wt");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return "http://localhost:8080";
  }

  @Override
  public void stopSut() {
    context.stop();
  }

  @Override
  public void resetStateOfSUT() {
    DbCleaner.clearDatabase_Postgres(connection, "public", null);
  }

  @Override
  public List<DbSpecification> getDbSpecifications() {
    return List.of(new DbSpecification(DatabaseType.POSTGRES, connection));
  }
}
