package com.Matheuszin.repository;

import com.Matheuszin.commons.UserUtils;
import com.Matheuszin.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class UserHardCodedRepositoryTest {
    @InjectMocks
    private  UserHardCodedRepository repository;
    @Mock
    private UserData userData;
    private List<User> userList = new ArrayList<>();
    @InjectMocks
    private UserUtils userUtils;

    @BeforeEach
    void init(){
        userList = userUtils.newUserList();
    }

    @Order(1)
    @DisplayName("findAll should returns a list with all users")
    @Test
    void findAll_ReturnsAllUsers_WhenSuccessful(){
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var users = repository.findAll();
        assertThat(users).isNotNull().hasSize(users.size());

    }

    @Order(2)
    @DisplayName("findById should returns a user with given id")
    @Test
    void findById_ReturnsAllUsersById_WhenSuccessful(){
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var expectedUser = userList.getFirst();
        var users = repository.findById(expectedUser.getId());
        Assertions.assertThat(users).contains(expectedUser);

    }

    @Order(3)
    @DisplayName("findByFirstName should returns empty list when name is null")
    @Test
    void findByName_ReturnsEmptyList_WhenNameIsNull(){
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var users = repository.findByFirstName(null);
        org.assertj.core.api.Assertions.assertThat(users).isNotNull().isEmpty();

    }

    @Order(4)
    @DisplayName("findByFirstName should returns list with found object when name exists")
    @Test
    void findByName_ReturnsFoundUserInList_WhenNameIsFound(){
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var expectedUser = userList.getFirst();

        var users = repository.findByFirstName(expectedUser.getFirstName());
        Assertions.assertThat(users).hasSize(1);

    }
    @Order(5)
    @DisplayName("save creates an user")
    @Test
    void save_CreatesUser_WhenSuccessful(){
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var producerToSave = userUtils.newUserToSave();
        var user = repository.save(producerToSave);

        Assertions.assertThat(user).isEqualTo(producerToSave).hasNoNullFieldsOrProperties();

        var producerSaveOptional = repository.findById(producerToSave.getId());

        Assertions.assertThat(producerSaveOptional).isPresent().contains(producerToSave);

    }

    @Order(6)
    @DisplayName("delete removes an user")
    @Test
    void delete_RemoveUser_WhenSuccessful(){
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var userToDelete = userList.getFirst();
        repository.delete(userToDelete);

        var users = repository.findAll();

        Assertions.assertThat(users).isNotEmpty().doesNotContain(userToDelete);

    }

    @Order(7)
    @DisplayName("update updates an user")
    @Test
    void update_UpdatesUser_WhenSuccessful(){
        BDDMockito.when(userData.getUser()).thenReturn(userList);
        var userToUpdate = userList.getFirst();
        userToUpdate.setFirstName("Henrique");

        repository.update(userToUpdate);

        var producerUpdatedOptional = repository.findById(userToUpdate.getId());

        Assertions.assertThat(producerUpdatedOptional).isPresent();
        Assertions.assertThat(producerUpdatedOptional.get().getFirstName()).isEqualTo(userToUpdate.getFirstName());

    }
}