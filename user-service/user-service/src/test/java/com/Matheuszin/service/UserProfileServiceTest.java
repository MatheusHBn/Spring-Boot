package com.Matheuszin.service;

import com.Matheuszin.commons.ProfileUtils;
import com.Matheuszin.commons.UserProfileUtils;
import com.Matheuszin.commons.UserUtils;
import com.Matheuszin.domain.UserProfile;
import com.Matheuszin.repository.UserProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserProfileServiceTest {

    @InjectMocks
    private UserProfileService service;
    @Mock
    private UserProfileRepository repository;
    private List<UserProfile> userProfileList;

    @InjectMocks
    private UserProfileUtils userProfileUtils;
    @Spy
    private UserUtils userUtils;
    @Spy
    private ProfileUtils profileUtils;

    @BeforeEach
    void init() {
        userProfileList = userProfileUtils.newUserProfileList();
    }

    @Order(1)
    @DisplayName("findAll() returns a list with all user profiles)")
    @Test
    void findAll_ReturnsAllUsers_WhenNameIsNull() {
        BDDMockito.when(repository.retrieveAll()).thenReturn(userProfileList);

        var userProfiles = service.findAll();

        Assertions.assertThat(userProfiles).isNotNull().hasSameElementsAs(userProfileList);
        userProfiles.forEach(userProfile -> Assertions.assertThat(userProfile).hasNoNullFieldsOrProperties());

    }

    @Order(2)
    @DisplayName("findAllUsersByProfileId() returns a list of users for a given profiles")
    @Test
    void findAllPaginated_ReturnsAllUsers_WhenSuccessful() {
        var profileId = 99L;
        var usersByProfile = this.userProfileList.stream().filter(userProfile -> userProfile.getProfile().getId().equals(profileId))
                .map(UserProfile::getUser).toList();

        BDDMockito.when(repository.findAllUsersByProfileId(profileId)).thenReturn(usersByProfile);

        var users = service.findAllUsersByProfileId(profileId);

        Assertions.assertThat(users).hasSize(1).doesNotContainNull().hasSameElementsAs(usersByProfile);

        users.forEach(userProfile -> Assertions.assertThat(userProfile).hasNoNullFieldsOrProperties());

    }
}