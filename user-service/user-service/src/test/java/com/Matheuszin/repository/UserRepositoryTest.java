package com.Matheuszin.repository;

import com.Matheuszin.commons.UserUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserUtils.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class UserRepositoryTest {
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserUtils userUtils;

    @Order(1)
    @DisplayName("save creates a user")
    @Test
    void save_CreatesUser_WhenSuccessful() {
        var userToSave = userUtils.newUserToSaveDB();
        var savedUser = repository.save(userToSave);
        Assertions.assertThat(savedUser).hasNoNullFieldsOrProperties();
        Assertions.assertThat(savedUser.getId()).isNotNull();
    }

    @Order(2)
    @DisplayName("findAll returns a list with all users")
    @Test
    @Sql("/sql/init_one_user.sql")
    void findAll_ReturnsAllUsers_WhenSuccessful() {
        var users = repository.findAll();
        Assertions.assertThat(users).isNotEmpty();
    }
}