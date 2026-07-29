package Matheuszin_springboot.Principal.controller;

import Matheuszin_springboot.Principal.domain.User;
import Matheuszin_springboot.Principal.repository.UserData;
import Matheuszin_springboot.Principal.repository.UserHardCodedRepository;
import Matheuszin_springboot.commons.FileUtils;
import Matheuszin_springboot.commons.UserUtils;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(controllers = UserControllerTest.class)
class UserControllerTest {
    private static final String URL = "/v1/users/list";
    private static final String URL2 = "/v1/users";

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
    void findAll_ReturnsAllUsers_WhenNameIsNull() throws Exception {
        BDDMockito.when(userData.getUserList()).thenReturn(userList);
        var response = fileUtils.readResourceFile("user/get-user-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(2)
    @DisplayName("GET v1/users?name=Nubank returns list with found object when name exists")
    @Test
    void findAll_ReturnsFoundUserInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(userData.getUserList()).thenReturn(userList);
        var response = fileUtils.readResourceFile("user/get-user-Matheus-name-200.json");
        var name = "Matheus";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("firstName", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(3)
    @DisplayName("GET v1/users?name=x returns empty list when name is not found")
    @Test
    void findAll2_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(userData.getUserList()).thenReturn(userList);
        var response = fileUtils.readResourceFile("user/get-user-x-name-200.json");
        var name = "x";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(4)
    @DisplayName("GET v1/users/1 returns a user id when name is null")
    @Test
    void findById_ReturnsUserId_WhenNameIsNull() throws Exception {
        BDDMockito.when(userData.getUserList()).thenReturn(userList);
        var response = fileUtils.readResourceFile("user/get-user-by-id-200.json");
        var id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(URL2+"/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Order(5)
    @DisplayName("GET v1/users/99 throws NotFound when user is not found")
    @Test
    void findById_ThrowsNotFound_WhenUserIsNotFound() throws Exception {
        BDDMockito.when(userData.getUserList()).thenReturn(userList);

        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));
    }
    @Order(6)
    @DisplayName("POST v1/users creates a user")
    @Test
    void save_CreatesUser_WhenSuccessful() throws Exception {
        var response = fileUtils.readResourceFile("user/post-response-user-201.json");
        var request = fileUtils.readResourceFile("user/post-request-user-200.json");
        var userToSave = userUtils.newUserToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(userToSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/users")
                        .content(request).header("x-api-key", "v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @SneakyThrows
    @Order(7)
    @DisplayName("PUT v1/user updates a user")
    @Test
    void update_UpdatesUser_WhenSuccessful() {
        var request = fileUtils.readResourceFile("user/put-user-by-id-200.json");

        BDDMockito.when(userData.getUserList()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/users")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Order(8)
    @DisplayName("update throws NotFound when user is not found")
    @Test
    void update_ThrowsNotFound_WhenUserIsNotFound() throws Exception {

        var request = fileUtils.readResourceFile("user/put-user-by-id-404.json");
        BDDMockito.when(userData.getUserList()).thenReturn(userList);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/users")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Order(9)
    @DisplayName("DELETE v1/users/1 creates a user")
    @Test
    void delete_RemoveUser_WhenSuccessful() throws Exception {
        BDDMockito.when(userData.getUserList()).thenReturn(userList);
        var id = userList.getFirst().getId();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/users/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Order(10)
    @DisplayName("DELETE v11/users/99 throws NotFound when user is not found")
    @Test
    void delete_ThrowsNotFound_WhenUserIsNotFound() throws Exception {
        BDDMockito.when(userData.getUserList()).thenReturn(userList);
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/users/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found"));

    }

    @Order(11)
    @ParameterizedTest
    @DisplayName("POST v1/users returns bad request when fields are empty")
    @MethodSource("postUserBadRequestSource")
    void save_ReturnsBadRequest_WhenFieldAreEmpty(String fileName, List<String> errors) throws Exception {

        var request = fileUtils.readResourceFile("user/%s".formatted(fileName));

        var mock = mockMvc.perform(MockMvcRequestBuilders
                        .post(URL2)
                        .content(request).header("x-api-key", "v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        var resolvedException = mock.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();


        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    @Order(12)
    @DisplayName("POST v1/users returns bad request when fields are empty")
    @Test
    void save_ReturnsBadRequest_WhenFieldAreBlank() throws Exception {

        var request = fileUtils.readResourceFile("user/post-request-user-blan k-fields-200.json");

        var mock = mockMvc.perform(MockMvcRequestBuilders
                        .post(URL2)
                        .content(request).header("x-api-key", "v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        var resolvedException = mock.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();

        var idError = "the field 'id' is required";

        Assertions.assertThat(resolvedException.getMessage()).contains(idError);
    }

    private static Stream<Arguments> postUserBadRequestSource(){
        var idError = "the field 'id' is required";
        var allErrors = List.of(idError);
        return Stream.of(Arguments.of("user/post-request-user-empty-fields-200.json", allErrors),
                         Arguments.of("user/post-request-user-blank-fields-200.json", allErrors),
                         Arguments.of("user/post-request-invalid-user-email-200", allErrors));
    }
}