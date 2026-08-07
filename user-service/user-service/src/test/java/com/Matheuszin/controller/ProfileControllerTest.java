package com.Matheuszin.controller;


import com.Matheuszin.commons.FileUtils;
import com.Matheuszin.commons.ProfileUtils;
import com.Matheuszin.domain.Profile;
import com.Matheuszin.repository.ProfileRepository;
import com.Matheuszin.repository.UserRepository;
import com.Matheuszin.service.UserProfileService;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(controllers = ProfileController.class)
@ComponentScan(basePackages = {"com.Matheuszin"})
class ProfileControllerTest {
    private static final String URL = "/v1/profiles";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FileUtils fileUtils;
    private List<Profile> profileList = new ArrayList<>();
    @Autowired
    private ProfileUtils profileUtils;

    @MockitoBean
    private ProfileRepository repository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private UserProfileService userProfileService;

    @BeforeEach
    void init() {
        profileList = profileUtils.newProfileList();
    }
    @Order(1)
    @DisplayName("GET v1/profiles should returns a list with all profiles")
    @Test
    void findAll_ReturnsAllProfiles_WhenSuccessful() throws Exception {
        BDDMockito.when(repository.findAll()).thenReturn(profileList);
        var response = fileUtils.readResourceFile("profile/get-profiles-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(2)
    @DisplayName("GET v1/profiles?firstName=x returns empty list when nothing is not found")
    @Test
    void findAll_ReturnsEmptyList_WhenFirstNothingIsNotFound() throws Exception {
        var verifyError = fileUtils.readResourceFile("profile/get-profile-empty-list-200.json");
        var firstName = "x";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", firstName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(verifyError));
    }

    @Order(3)
    @DisplayName("POST v1/profiles creates a profile")
    @Test
    void save_CreatesProfile_WhenSuccessful() throws Exception {
        var verifyError = fileUtils.readResourceFile("profile/post-response-profile-201.json");
        var request = fileUtils.readResourceFile("profile/post-request-profile-200.json");
        var profileSaved = profileUtils.newProfileSaved();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(profileSaved);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(verifyError));

    }

    @Order(4)
    @DisplayName("POST v1/profiles returns bad request when field are empty")
    @MethodSource("postProfileBadRequestSource")
    @ParameterizedTest
    void save_ReturnsBadRequest_WhenFieldAreEmpty(String fileName, List<String> errors) throws Exception {
        var verifyError = fileUtils.readResourceFile("profile/%s".formatted(fileName));
        var mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL).content(verifyError).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        Exception resolvedException = mvcResult.getResolvedException();
        Assertions.assertThat(resolvedException).isNotNull();


        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    private static Stream<Arguments> postProfileBadRequestSource(){
        var nameError = "the field is required";
        var descriptionError = "the field is required";

        var allErrors = List.of(nameError, descriptionError);
        return Stream.of(Arguments.of("post-request-profile-empty-field-400.json", allErrors),
                Arguments.of("post-request-profile-blank-field-400.json", allErrors));
    }
}