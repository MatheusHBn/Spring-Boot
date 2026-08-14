package com.Matheuszin.controller;

import com.Matheuszin.commons.FileUtils;
import com.Matheuszin.commons.UserUtils;
import com.Matheuszin.config.IntegrationTestConfig;
import com.Matheuszin.config.TestContainersConfiguration;
import com.Matheuszin.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import net.javacrumbs.jsonunit.core.Option;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@Import(TestContainersConfiguration.class)
@ActiveProfiles("itest")
public class UserControllerRestAssuredIT extends IntegrationTestConfig {

    private static final String URL = "/v1/users";

    @Autowired
    private UserRepository repository;

    @Autowired
    private FileUtils fileUtils;
    @LocalServerPort
    private int port;

    @Autowired
    private UserUtils userUtils;

    @BeforeEach
    void setUrl() {
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.port = port;
    }

    @Order(1)
    @DisplayName("GET v1/users returns a list with all users when argument is null")
    @Sql(value = "/sql/user/init_three_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/user/clean_code_user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void findAll_ReturnsAllUsers_WhenArgumentIsNull() throws Exception {
       var response = fileUtils.readResourceFile("user/get-user-null-first-name-200.json");

       RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
               .when().get(URL).then().statusCode(HttpStatus.OK.value()).body(Matchers.equalTo(response)).log();

    }

    @Order(2)
    @DisplayName("GET v1/users?firstName=Matheus returns list with found object when first name exists")
    @Sql(value = "/sql/user/init_three_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/user/clean_code_user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void findAll_ReturnsFoundUserInList_WhenFirstNameIsFound() throws Exception{
        var expectedResponse = fileUtils.readResourceFile("user/get-user-Matheus-first-name-200.json");

        var response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().queryParam("firstName", "Matheus")
                .get(URL).then().statusCode(HttpStatus.OK.value()).log().all().extract().response().body().asString();

        JsonAssertions.assertThatJson(response).whenIgnoringPaths("[*].id").isEqualTo(expectedResponse);
    }

    @Order(3)
    @DisplayName("GET v1/users?firstName=x returns empty list when name is not found")
    @Sql(value = "/sql/user/init_three_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/user/clean_code_user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void findAll_ReturnsEmptyList_WhenFirstNameIsNotFound() throws Exception {
        var expectedResponse = fileUtils.readResourceFile("user/get-user-x-first-name-200.json");
        var firstName = "x";
        var response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().queryParam("firstName", firstName)
                .get(URL).then().statusCode(HttpStatus.OK.value()).log().all().extract().response().body().asString();

        JsonAssertions.assertThatJson(response).whenIgnoringPaths("[*].id").isEqualTo(expectedResponse);

    }

    @Order(4)
    @DisplayName("GET v1/users/1 returns an user id when name is null")
    @Sql(value = "/sql/user/init_three_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/user/clean_code_user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void findById_ReturnsUserId_WhenNameIsNull() throws Exception {
        var expectedResponse = fileUtils.readResourceFile("user/get-user-by-id-200.json");
        var id = 10L;
        var response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().pathParams("id", id)
                .get(URL+"/{id}").then().statusCode(HttpStatus.OK.value()).log().all().extract().response().body().asString();

        JsonAssertions.assertThatJson(response).whenIgnoringPaths("id").isEqualTo(expectedResponse);
    }

    @Order(5)
    @Test
    @Sql(value = "/sql/user/init_three_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/user/clean_code_user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get v1/users/99 throws NotFound 404 when user is not found")
    void findById_ThrowsNotFound_WhenUserIsNotFound() throws Exception {
        var expectedResponse = fileUtils.readResourceFile("user/user-404.json");
        var id = 99L;

        var response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().pathParams("id", id)
                .get(URL + "/{id}").then().statusCode(HttpStatus.NOT_FOUND.value()).body(Matchers.equalTo(expectedResponse)).log().all();

        JsonAssertions.assertThatJson(response).whenIgnoringPaths("timestamp").when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(expectedResponse);
    }

    @Order(6)
    @DisplayName("POST v1/users creates a user")
    @Sql(value = "/sql/user/clean_code_user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void save_CreatesUser_WhenSuccessful() throws Exception {
        var expectedResponse = fileUtils.readResourceFile("user/post-response-user-201.json");
        var request = fileUtils.readResourceFile("user/post-request-user-200.json");
        var response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().body(request).post(URL).then().statusCode(HttpStatus.ACCEPTED.value()).log().all().extract().response().body().asString();

        JsonAssertions.assertThatJson(response)
                .node("id")
                .asNumber()
                .isPositive();


        JsonAssertions.assertThatJson(response)
                .when(Option.IGNORING_EXTRA_FIELDS)
                .whenIgnoringPaths("id")
                .isEqualTo(expectedResponse);

    }

    @Order(7)
    @DisplayName("DELETE v1/users/1 removes an user")
    @Sql(value = "/sql/user/init_one_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/user/clean_code_user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void delete_RemoveUser_WhenSuccessful() throws Exception {
        var id = repository.findAll().getFirst().getId();

        RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().pathParams("id", id)
                .delete(URL+"/{id}").then().statusCode(HttpStatus.NO_CONTENT.value()).log().all().extract().response().body().asString();
    }


    @Order(8)
    @DisplayName("DELETE v1/producers/99 throws NotFound when producer is not found")
    @Sql(value = "/sql/user/clean_code_user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void delete_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        var expectedResponse = fileUtils.readResourceFile("user/user-404.json");
        var id = 99L;
        var response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().pathParams("id", id)
                .delete(URL + "/{id}").then().statusCode(HttpStatus.NOT_FOUND.value())
                .body(Matchers.equalTo(expectedResponse)).log().all();

        JsonAssertions.assertThatJson(response).whenIgnoringPaths("timestamp").when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(expectedResponse);
    }

    @SneakyThrows
    @Order(9)
    @DisplayName("PUT v1/user updates a user")
    @Sql(value = "/sql/user/init_one_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/user/clean_code_user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void update_UpdatesUser_WhenSuccessful() {
        var request = fileUtils.readResourceFile("user/put-request-user-by-id-200.json");
        var users = repository.findByFirstNameIgnoreCase("Thiago");

        Assertions.assertThat(users).hasSize(1);
        request = request.replace("1", users.getFirst().getId().toString());
        RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().body(request).put(URL).then().statusCode(HttpStatus.NO_CONTENT.value()).log().all();

    }

    @Order(10)
    @DisplayName("update throws NotFound when user is not found")
    @Sql(value = "/sql/user/clean_code_user.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void update_ThrowsNotFound_WhenUserIsNotFound() throws Exception {
        var request = fileUtils.readResourceFile("user/put-request-user-by-id-404.json");
        var expectedResponse = fileUtils.readResourceFile("user/user-404.json");

        var response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request).when().post(URL).then().statusCode(HttpStatus.BAD_REQUEST.value())
                .log().all().extract().response().body().asString();

        JsonAssertions.assertThatJson(response).whenIgnoringPaths("timestamp").when(Option.IGNORING_ARRAY_ORDER)
                .isEqualTo(expectedResponse);
    }

    @Order(11)
    @DisplayName("POST v1/users returns bad request when field are empty")
    @MethodSource("postUserBadRequestSource")
    @ParameterizedTest
    void save_ReturnsBadRequest_WhenFieldAreEmpty(String fileName, String responseFile) throws Exception {
        var request = fileUtils.readResourceFile("user/%s".formatted(fileName));
        var expectedResponse = fileUtils.readResourceFile("user/%s".formatted(responseFile));


        var response = RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
                .when().post(URL).then().statusCode(HttpStatus.BAD_REQUEST.value())
                .log().all().extract().asString();


        JsonAssertions.assertThatJson(response)
                .whenIgnoringPaths("timestamp")
                .isEqualTo(expectedResponse);



    }

    private static Stream<Arguments> postUserBadRequestSource() {
        return Stream.of(
                Arguments.of("post-request-user-empty-field-400.json", "post-response-user-empty-field-400.json"),
                Arguments.of("post-request-user-blank-field-400.json", "post-response-user-empty-field-400.json")
        );
    }
}