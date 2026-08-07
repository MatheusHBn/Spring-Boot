package com.Matheuszin.service;

import com.Matheuszin.commons.UserUtils;
import com.Matheuszin.domain.User;
import com.Matheuszin.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserUtils userUtils;
    private List<User> userList;

    @BeforeEach
    void init() {
        userList = userUtils.newUserList();
    }

    @Order(1)
    @DisplayName("findAll should returns a list with all users, when name is null")
    @Test
    void findAll_ReturnsAllUsers_WhenNameIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(userList);

        var users = service.findAll(null);

        Assertions.assertThat(users).isNotNull().hasSize(users.size());

    }

    @Order(1)
    @DisplayName("GET v1/users/paginated should returns a paginated list with all users, when name is null")
    @Test
    void findAllPaginated_ReturnsAllUsers_WhenSuccessful(){
        var pageRequest = PageRequest.of(0, userList.size());
        PageImpl<User> pageUser = new PageImpl<>(userList, pageRequest, 1);

        BDDMockito.when(repository.findAll(BDDMockito.any(Pageable.class))).thenReturn(pageUser);

        Page<User> userFound = service.findAllPaged(pageRequest);

        Assertions.assertThat(userFound).isNotNull().hasSameElementsAs(userList);
    }

    @Order(2)
    @DisplayName("findAll should returns a list with all users, when firstName is null")
    @Test
    void findByName_ReturnsAllUsers_WhenFirstNameIsNull() {
        var user = userList.getFirst();
        var expectedUsersFound = singletonList(user);
        BDDMockito.when(repository.findByFirstNameIgnoreCase(user.getFirstName())).thenReturn(expectedUsersFound);
        var usersFound = service.findAll(user.getFirstName());
        Assertions.assertThat(usersFound).containsAll(expectedUsersFound);

    }

    @Order(3)
    @DisplayName("findByName should returns empty list when firstName is not found")
    @Test
    void findByName_ReturnsEmptyListUser_WhenFirstNameIsNotFound() {
        var firstName = "not-found";
        BDDMockito.when(repository.findByFirstNameIgnoreCase(firstName)).thenReturn(emptyList());
        var users = service.findAll(firstName);
        Assertions.assertThat(users).isNotNull().isEmpty();
    }

    @Order(4)
    @DisplayName("findById returns user id when name is null")
    @Test
    void findById_ReturnsUserId_WhenNameIsNull() {
        var expectedUser = userList.getFirst();
        BDDMockito.when(repository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        var users = service.findByIdOrThrowNotFound(expectedUser.getId());
        Assertions.assertThat(users).isEqualTo(expectedUser);
    }

    @Order(5)
    @DisplayName("findById throws ResponseStatusException when user is not found")
    @Test
    void findById_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var expectedUser = userList.getFirst();
        BDDMockito.when(repository.findById(expectedUser.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.findByIdOrThrowNotFound(expectedUser.getId())).isInstanceOf(ResponseStatusException.class);
    }

    @Order(6)
    @DisplayName("save creates a user")
    @Test
    void save_CreatesUser_WhenSuccessful() {
        var userToSave = userUtils.newUserToSave();

        BDDMockito.when(repository.save(userToSave)).thenReturn(userToSave);
        var savedUser = service.save(userToSave);
        Assertions.assertThat(savedUser).isEqualTo(userToSave).hasNoNullFieldsOrProperties();
    }

    @Order(7)
    @DisplayName("delete removes an user")
    @Test
    void delete_RemoveUser_WhenSuccessful() {
        var userToDelete = userList.getFirst();
        BDDMockito.when(repository.findById(userToDelete.getId())).thenReturn(Optional.of(userToDelete));
        BDDMockito.doNothing().when(repository).delete(userToDelete);
        Assertions.assertThatNoException().isThrownBy(() -> service.delete(userToDelete.getId()));
    }

    @Order(8)
    @DisplayName("delete throws ResponseStatusException when user is not found")
    @Test
    void delete_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var userToDelete = userList.getFirst();
        BDDMockito.when(repository.findById(userToDelete.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.delete(userToDelete.getId())).isInstanceOf(ResponseStatusException.class);

    }

    @Order(9)
    @DisplayName("update a user")
    @Test
    void update_UpdatesUser_WhenSuccessful() {
        var userToUpdate = userList.getFirst();
        userToUpdate.setFirstName("Matheus");

        BDDMockito.when(repository.findById(userToUpdate.getId())).thenReturn(Optional.of(userToUpdate));
        BDDMockito.when(repository.save(userToUpdate)).thenReturn(userToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(userToUpdate));

    }

    @Order(10)
    @DisplayName("update throws ResponseStatusException when user is not found")
    @Test
    void update_ThrowsResponseStatusException_WhenUserIsNotFound() {
        var userToUpdate = userList.getFirst();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.update(userToUpdate)).isInstanceOf(ResponseStatusException.class);

    }
}