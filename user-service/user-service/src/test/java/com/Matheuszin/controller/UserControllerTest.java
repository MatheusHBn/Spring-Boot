package com.Matheuszin.controller;


import com.Matheuszin.commons.FileUtils;
import com.Matheuszin.commons.UserUtils;
import com.Matheuszin.domain.User;
import com.Matheuszin.repository.UserData;
import com.Matheuszin.repository.UserHardCodedRepository;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(controllers = UserController.class)
@ComponentScan(basePackages = {"com.Matheuszin"})
class UserControllerTest {
    private static final String URL = "/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private UserHardCodedRepository repository;
    @Autowired
    private FileUtils fileUtils;
    @MockitoBean
    private UserData userData;
    private List<User> userList = new ArrayList<>();
    @Autowired
    private UserUtils userUtils;

    @BeforeEach
    void init() {
        userList = userUtils.newUserList();
    }

    @Order(1)
    @DisplayName("GET v1/users should returns a list with all users, when name is null")
    @Test
    void findAll_ReturnsAllUsers_WhenFirstNameIsNull() throws Exception {
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var response = fileUtils.readResourceFile("user/get-user-null-first-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(2)
    @DisplayName("GET v1/users?firstName= Matheus returns list with found object when name exists")
    @Test
    void findAll_ReturnsFoundUserInList_WhenFirstNameIsFound() throws Exception {
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var verifyError = fileUtils.readResourceFile("user/get-user-Matheus-first-name-200.json");
        var firstName = "Matheus";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("firstName", firstName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(verifyError));
    }

    @Order(3)
    @DisplayName("GET v1/users?firstName=x returns empty list when name is not found")
    @Test
    void findAll_ReturnsEmptyList_WhenFirstNameIsNotFound() throws Exception {
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var verifyError = fileUtils.readResourceFile("user/get-user-x-first-name-200.json");
        var firstName = "x";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("firstName", firstName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(verifyError));
    }

    @Order(4)
    @DisplayName("GET v1/users/1 returns an user id when name is null")
    @Test
    void findById_ReturnsUserId_WhenNameIsNull() throws Exception {
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var verifyError = fileUtils.readResourceFile("user/get-user-by-id-200.json");
        var id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(verifyError));
    }

    @Order(5)
    @Test
    @DisplayName("Get v1/users/99 throws NotFound 404 when user is not found")
    void findById_ThrowsNotFound_WhenUserIsNotFound() throws Exception {
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));
    }

    @Order(6)
    @DisplayName("POST v1/users creates a user")
    @Test
    void save_CreatesUser_WhenSuccessful() throws Exception {
        var verifyError = fileUtils.readResourceFile("user/post-response-user-201.json");
        var request = fileUtils.readResourceFile("user/post-request-user-200.json");
        var producerToSave = userUtils.newUserToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(producerToSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(verifyError));

    }

    @Order(7)
    @DisplayName("DELETE v1/users/1 removes an user")
    @Test
    void delete_RemoveUser_WhenSuccessful() throws Exception {
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var id = userList.getFirst().getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Order(8)
    @DisplayName("DELETE v1/producers/99 throws NotFound when producer is not found")
    @Test
    void delete_ThrowsNotFound_WhenProducerIsNotFound() throws Exception {
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));

    }

    @SneakyThrows
    @Order(9)
    @DisplayName("PUT v1/user updates a user")
    @Test
    void update_UpdatesUser_WhenSuccessful() {
        var request = fileUtils.readResourceFile("user/put-request-user-by-id-200.json");

        BDDMockito.when(userData.getUser()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Order(10)
    @DisplayName("update throws NotFound when user is not found")
    @Test
    void update_ThrowsNotFound_WhenUserIsNotFound() throws Exception {

        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var request = fileUtils.readResourceFile("user/put-request-user-by-id-404.json");
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));
    }

    @Order(11)
    @DisplayName("POST v1/users returns bad request when field are empty")
    @MethodSource("postUserBadRequestSource")
    @ParameterizedTest
    void save_ReturnsBadRequest_WhenFieldAreEmpty(String fileName, List<String> errors) throws Exception {
        var verifyError = fileUtils.readResourceFile("user/%s".formatted(fileName));
        var mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URL).content(verifyError).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        Exception resolvedException = mvcResult.getResolvedException();
        Assertions.assertThat(resolvedException).isNotNull();


        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    private static Stream<Arguments> postUserBadRequestSource(){
        var firstNameError = "the field is required";
        var lastNameError = "the field is required";
        var emailErrorMessage = "email is not valid";
        var emailInvalidError = "email is not valid";

        var allErrors = List.of(firstNameError, lastNameError, emailErrorMessage);
        var emailError = Collections.singletonList(emailInvalidError);
        return Stream.of(Arguments.of("post-request-user-empty-field-400.json", allErrors),
                Arguments.of("post-request-user-blank-field-400.json", allErrors),
                Arguments.of("post-request-user-invalid-email-400.json", emailError));
    }
}