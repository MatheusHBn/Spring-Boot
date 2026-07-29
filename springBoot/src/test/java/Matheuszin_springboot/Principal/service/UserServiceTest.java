package Matheuszin_springboot.Principal.service;

import Matheuszin_springboot.Principal.domain.User;
import Matheuszin_springboot.Principal.repository.UserHardCodedRepository;
import Matheuszin_springboot.commons.UserUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;
    @InjectMocks
    private UserUtils userUtils;
    @Mock
    private UserHardCodedRepository repository;
    private List<User> userList;

    @BeforeEach
    void init() {
        userList = userUtils.newUserList();
    }


    @Order(1)
    @DisplayName("findAll should returns a list with all users, when name is null")
    @Test
    void findAll_ReturnsAllusers_WhenNameIsNull() {
        BDDMockito.when(repository.findAllUsers()).thenReturn(userList);

        var users = service.findAllUserFirstName(null);

        Assertions.assertThat(users).isNotNull().hasSameElementsAs(userList);
    }

    @Order(2)
    @DisplayName("findAll should returns a list with all users, when name is null")
    @Test
    void findByName_ReturnsAllUsers_WhenNameIsNull() {
        var users = userList.getFirst();
        var expectedUsersFound = singletonList(users);
        BDDMockito.when(repository.findByFirstNameUser(users.getFirstName())).thenReturn(expectedUsersFound);
        var usersFound = service.findAllUser();
        Assertions.assertThat(usersFound).containsAll(expectedUsersFound);

    }

    @Order(3)
    @DisplayName("findByName should returns empty list when name is not found")
    @Test
    void findByName_ReturnsEmptyListUser_WhenNameIsNotFound() {
        var name = "Matheus";
        BDDMockito.when(repository.findByFirstNameUser(name)).thenReturn(emptyList());
        var users = service.findAllUserFirstName(name);
        Assertions.assertThat(users).isNotNull().isEmpty();
    }

    @Order(4)
    @DisplayName("findByHertz returns user Hertz when name is null")
    @Test
    void findByHertz_ReturnsUserHertz_WhenNameIsNull() {
        var expectedUser = userList.getFirst();
        BDDMockito.when(repository.findByIdUser(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        var users = service.findByIdOrThrowNotFoundUser(expectedUser.getId());
        Assertions.assertThat(users).isEqualTo(expectedUser);
    }

    @Order(5)
    @DisplayName("findByHertz throws ResponseStatusException when user is not found")
    @Test
    void findByHertz_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var expectedUser = userList.getFirst();
        BDDMockito.when(repository.findByIdUser(expectedUser.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.findByIdOrThrowNotFoundUser(expectedUser.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Order(6)
    @DisplayName("save creates a user")
    @Test
    void save_CreatesUser_WhenSuccessful() {
        var UserToSave = userUtils.newUserToSave();

        BDDMockito.when(repository.save(UserToSave)).thenReturn(UserToSave);
        var savedUser = service.save(UserToSave);
        Assertions.assertThat(savedUser).isEqualTo(UserToSave).hasNoNullFieldsOrProperties();
    }

    @Order(7)
    @DisplayName("save creates a user")
    @Test
    void delete_RemoveUser_WhenSuccessful() {
        var UserToDelete = userList.getFirst();
        BDDMockito.when(repository.findByIdUser(UserToDelete.getId())).thenReturn(Optional.of(UserToDelete));
        BDDMockito.doNothing().when(repository).deleteByIdUser(UserToDelete);
        Assertions.assertThatNoException().isThrownBy(() -> service.deleteByIdUser(UserToDelete.getId()));
    }

    @Order(8)
    @DisplayName("delete throws ResponseStatusException when user is not found")
    @Test
    void delete_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var UserToDelete = userList.getFirst();
        BDDMockito.when(repository.findByIdUser(UserToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.deleteByIdUser(UserToDelete.getId())).isInstanceOf(ResponseStatusException.class);

    }

    @Order(9)
    @DisplayName("update a user")
    @Test
    void update_UpdatesUser_WhenSuccessful() {
        var userToUpdate = userList.getFirst();
        userToUpdate.setFirstName("Matheus");
        BDDMockito.when(repository.findByIdUser(userToUpdate.getId())).thenReturn(Optional.of(userToUpdate));
        BDDMockito.doNothing().when(repository).update(userToUpdate);
        service.update(userToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(userToUpdate));

    }

    @Order(10)
    @DisplayName("update throws ResponseStatusException when user is not found")
    @Test
    void update_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var userToUpdate = userList.getFirst();

        BDDMockito.when(repository.findByIdUser(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.update(userToUpdate)).isInstanceOf(ResponseStatusException.class);

    }
}