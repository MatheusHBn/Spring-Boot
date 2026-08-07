package com.Matheuszin.repository;

import com.Matheuszin.commons.UserUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserUtils.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class UserProfileRepositoryTest {
    @Autowired
    private UserProfileRepository repository;

    @Order(2)
    @DisplayName("findAll returns a list with all users")
    @Test
    @Sql("/sql/init_user_profile_2_users_1_profile.sql")
    void findAllUsersByProfilesId_ReturnsAllUsersByProfileId_WhenSuccessful() {
        var profileId = 1L;
        var users = repository.findAllUsersByProfileId(profileId);
        Assertions.assertThat(users).isNotEmpty().hasSize(2).doesNotContainNull();

        users.forEach(user -> Assertions.assertThat(user).hasNoNullFieldsOrProperties());
    }
}