package com.Matheuszin.commons;

import com.Matheuszin.domain.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileUtils {
    public List<Profile> newProfileList() {
        var profile = Profile.builder().id(1L).name("Admin").description("Admin can admins everything").build();
        var profile1 = Profile.builder().id(2L).name("Manager").description("Manager can manages users").build();

        return new ArrayList<>(List.of(profile, profile1));
    }

    public Profile newProfileToSave() {
        return Profile.builder().name("Regular User").description("Regular user with regular permissions").build();
    }

    public Profile newProfileSaved() {
        return Profile.builder().id(99L).name("Regular User").description("Regular user with regular permissions").build();
    }
}
