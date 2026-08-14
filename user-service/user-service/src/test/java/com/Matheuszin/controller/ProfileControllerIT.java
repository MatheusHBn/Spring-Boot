package com.Matheuszin.controller;

import com.Matheuszin.commons.FileUtils;
import com.Matheuszin.commons.ProfileUtils;
import com.Matheuszin.config.IntegrationTestConfig;
import com.Matheuszin.config.TestContainersConfiguration;
import com.Matheuszin.response.ProfileGetResponse;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureRestTestClient
@Import(TestContainersConfiguration.class)
@ActiveProfiles("itest")
public class ProfileControllerIT extends IntegrationTestConfig {

    private static final String URL = "/v1/profiles";

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private RestTestClient client;

    @Autowired
    private ProfileUtils profileUtils;

    @Order(1)
    @DisplayName("GET v1/profiles returns a list with all profiles")
    @Sql(value = "/sql/profile/init_two_profiles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/profile/clean_code_profile.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void findAll_ReturnsAllProfiles_WhenSuccessful() {
        var typeReference = new ParameterizedTypeReference<List<ProfileGetResponse>>() {
        };

        List<ProfileGetResponse> profiles = client.get()
                .uri(URL)
                .exchange()
                .expectStatus().isOk()
                .expectBody(typeReference)
                .returnResult()
                .getResponseBody();

        assertThat(profiles).isNotNull().doesNotContainNull();
        profiles.forEach(profile -> assertThat(profile).hasNoNullFieldsOrProperties());
    }

    @Order(2)
    @DisplayName("GET v1/profiles returns a list with all profiles")
    @Test
    void findAll_ReturnsEmptyList_WhenNothingIsFound() {
        var typeReference = new ParameterizedTypeReference<List<ProfileGetResponse>>() {
        };

        List<ProfileGetResponse> profiles = client.get()
                .uri(URL)
                .exchange()
                .expectStatus().isOk()
                .expectBody(typeReference)
                .returnResult()
                .getResponseBody();

        assertThat(profiles).isNotNull().isEmpty();
    }

    @Order(3)
    @DisplayName("GET v1/profiles returns a list with all profiles")
    @Test
    void save_CreatesProfile_WhenSuccessful() throws Exception {
        String profileToSave = fileUtils.readResourceFile("profile/post-request-profile-200.json");

        // Corrigido enviando como String pura para o backend ler o JSON direto
        var responseEntity = client.post()
                .uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(profileToSave)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProfileGetResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseEntity).isNotNull().hasNoNullFieldsOrProperties();
    }

    @Order(4)
    @DisplayName("POST v1/profiles returns bad request when field are invalid")
    @MethodSource("postProfileBadRequestSource")
    @ParameterizedTest
    void save_ReturnsBadRequest_WhenFieldAreInvalid(String requestFile, String responseFile) throws Exception {
        var verifyErrorJson = fileUtils.readResourceFile("profile/%s".formatted(requestFile));
        var verifyExpectedJson = fileUtils.readResourceFile("profile/%s".formatted(responseFile));

        // Refatorado sem HttpEntity e extraindo a String do erro da resposta
        var responseEntity = client.post()
                .uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(verifyErrorJson)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        JsonAssertions.assertThatJson(responseEntity)
                .whenIgnoringPaths("timestamp")
                .isEqualTo(verifyExpectedJson);
    }

    private static Stream<Arguments> postProfileBadRequestSource() {
        return Stream.of(
                Arguments.of("post-request-profile-empty-field-400.json", "post-response-profile-empty-field-400.json"),
                Arguments.of("post-request-profile-blank-field-400.json", "post-response-profile-blank-field-400.json")
        );
    }
}