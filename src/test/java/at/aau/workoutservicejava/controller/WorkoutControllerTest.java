package at.aau.workoutservicejava.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@Disabled
@Sql("classpath:01_ddl.sql")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class WorkoutControllerTest {

  @LocalServerPort private int serverPort;

  @BeforeEach
  void setUpBeforeClass() throws Exception {
    RestAssured.port = serverPort;
    RestAssured.urlEncodingEnabled = false;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  @Test
  void test() {
    RestAssured.given()
        .accept(ContentType.JSON)
        .when()
        .get("/workouts")
        .then()
        .statusCode(HttpStatus.OK.value())
        .log().body();
  }
}
