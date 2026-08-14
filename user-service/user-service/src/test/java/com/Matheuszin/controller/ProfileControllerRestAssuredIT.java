package com.Matheuszin.controller;

import com.Matheuszin.commons.FileUtils;
import com.Matheuszin.commons.ProfileUtils;
import com.Matheuszin.config.IntegrationTestConfig;
import com.Matheuszin.config.TestContainersConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
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
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@Import(TestContainersConfiguration.class)
@ActiveProfiles("itest")
public class ProfileControllerRestAssuredIT extends IntegrationTestConfig {

    private static final String URL = "/v1/profiles";

    @Autowired
    private FileUtils fileUtils;
    @LocalServerPort
    private int port;

    @Autowired
    private ProfileUtils profileUtils;

    @BeforeEach
    void setUrl() {
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.port = port;
    }

    @Order(1)
    @DisplayName("GET v1/profiles returns a list with all profiles")
    @Sql(value = "/sql/profile/init_two_profiles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/profile/clean_code_profile.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void findAll_ReturnsAllProfiles_WhenSuccessful() throws Exception {
       var response = fileUtils.readResourceFile("profile/get-profiles-200.json");

       RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
               .when().get(URL).then().statusCode(HttpStatus.OK.value()).body(Matchers.equalTo(response)).log();

    }

    @Order(2)
    @DisplayName("GET v1/profiles returns a list with all profiles")
    @Test
    void findAll_ReturnsEmptyList_WhenNothingIsNotFound() throws Exception{
        var response = fileUtils.readResourceFile("profile/get-profiles-200.json");

        RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().get(URL).then().statusCode(HttpStatus.OK.value()).body(Matchers.equalTo(response)).log();
    }

    @Order(3)
    @DisplayName("POST v1/profiles creates a profile when successful")
    @Test
    void save_CreatesProfile_WhenSuccessful() throws Exception {
        var request = fileUtils.readResourceFile("profile/post-request-profile-200.json");
        var expectedResponse = fileUtils.readResourceFile("profile/post-response-profile-201.json");

        var response = RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
                .when().post(URL).then().statusCode(HttpStatus.ACCEPTED.value())
                .log().all().extract().asString();

        JsonAssertions.assertThatJson(response)
                .node("id")
                .asNumber()
                .isPositive();


        JsonAssertions.assertThatJson(response)
                .whenIgnoringPaths("id")
                .isEqualTo(expectedResponse);
    }

    @Order(4)
    @DisplayName("POST v1/profiles returns bad request when field are invalid")
    @MethodSource("postProfileBadRequestSource")
    @ParameterizedTest
    void save_ReturnsBadRequest_WhenFieldAreInvalid(String requestFile, String responseFile) throws Exception {
        var verifyErrorJson = fileUtils.readResourceFile("profile/%s".formatted(requestFile));
        var verifyExpectedJson = fileUtils.readResourceFile("profile/%s".formatted(responseFile));

        // Refatorado sem HttpEntity e extraindo a String do erro da resposta
        var response = RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON).body(verifyErrorJson)
                .when().post(URL).then().statusCode(HttpStatus.BAD_REQUEST.value())
                .log().all().extract().asString();

        // Validação usando a engine do JsonAssertions ignorando o campo dinâmico de tempo
        JsonAssertions.assertThatJson(response)
                .whenIgnoringPaths("timestamp")
                .isEqualTo(verifyExpectedJson);
    }

    private static Stream<Arguments> postProfileBadRequestSource() {
        return Stream.of(
                Arguments.of("post-request-profile-empty-field-400.json", "post-response-profile-empty-field-400.json"),
                Arguments.of("post-request-profile-blank-field-400.json", "post-response-profile-empty-field-400.json")
        );
    }
}