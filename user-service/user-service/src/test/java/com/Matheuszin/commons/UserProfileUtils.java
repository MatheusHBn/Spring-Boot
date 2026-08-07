package com.Matheuszin.commons;

import com.Matheuszin.domain.Profile;
import com.Matheuszin.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class UserProfileUtils {

    private final UserUtils userUtils;
    private final ProfileUtils profileUtils;

    public List<UserProfile> newUserProfileList() {
        var regulaUserProfile = newUserProfileSaved();
        return new ArrayList<>(List.of(regulaUserProfile));
    }

    public UserProfile newUserProfileToSave() {
        return UserProfile.builder().user(userUtils.newUserSaved()).profile(profileUtils.newProfileSaved()).build();
    }

    public UserProfile newUserProfileSaved() {
        return UserProfile.builder().id(99L).user(userUtils.newUserSaved()).profile(profileUtils.newProfileSaved()).build();
    }

}
