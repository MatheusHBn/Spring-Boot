package Matheuszin_springboot.Principal.repository;

import Matheuszin_springboot.Principal.domain.User;
import Matheuszin_springboot.commons.UserUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class UserHardCodedRepositoryTest {

    @InjectMocks
    private UserHardCodedRepository repository;
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
        BDDMockito.when(userData.getUserList()).thenReturn(userList);
        var users = repository.findAllUsers();
        Assertions.assertThat(users).isNotNull().hasSize(users.size());

    }

    @Order(2)
    @DisplayName("findById should returns a user with given id")
    @Test
    void findById_ReturnsAllUsersById_WhenSuccessful(){
        BDDMockito.when(userData.getUserList()).thenReturn(userList);
        var expectedUser = userList.getFirst();
        var users = repository.findByIdUser(expectedUser.getId());
        Assertions.assertThat(users).isPresent().contains(expectedUser);

    }

    @Order(3)

    @DisplayName("findByName should returns empty list when name is null")
    @Test
    void findByName_ReturnsEmptyList_WhenNameIsNull(){
        BDDMockito.when(userData.getUserList()).thenReturn(userList);
        var users = repository.findByFirstNameUser(null);
        Assertions.assertThat(users).isNotNull().isEmpty();

    }

    @Order(4)
    @DisplayName("findByName should returns list with found object when name exists")
    @Test
    void findByName_ReturnsFoundUserInList_WhenNameIsFound(){
        BDDMockito.when(userData.getUserList()).thenReturn(userList);
        var expectedUser = userList.getFirst();

        var users = repository.findByFirstNameUser(expectedUser.getFirstName());
        Assertions.assertThat(users).hasSize(1);

    }
}