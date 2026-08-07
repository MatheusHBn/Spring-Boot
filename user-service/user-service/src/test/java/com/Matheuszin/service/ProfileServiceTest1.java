package com.Matheuszin.service;

import com.Matheuszin.commons.ProfileUtils;
import com.Matheuszin.domain.Profile;
import com.Matheuszin.repository.ProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProfileServiceTest {

    @InjectMocks
    private ProfileService service;
    @Mock
    private ProfileRepository repository;

    @InjectMocks
    private ProfileUtils profileUtils;
    private List<Profile> profileList;

    @BeforeEach
    void init() {
        profileList = profileUtils.newProfileList();
    }

    @Order(1)
    @DisplayName("findAll should returns a list with all profiles")
    @Test
    void findAll_ReturnsAllProfiles_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(profileList);

        var profiles = service.findAll();

        org.assertj.core.api.Assertions.assertThat(profiles).isNotNull().hasSize(profiles.size());

    }

    @Order(2)
    @DisplayName("save creates a profile")
    @Test
    void save_CreatesProfile_WhenSuccessful() {
        var profileToSave = profileUtils.newProfileToSave();
        var profileSaved = profileUtils.newProfileSaved();

        BDDMockito.when(repository.save(profileToSave)).thenReturn(profileSaved);
        var savedProfile = service.save(profileToSave);
        Assertions.assertThat(savedProfile).isEqualTo(profileSaved).hasNoNullFieldsOrProperties();
    }
}