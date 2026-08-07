package com.Matheuszin.service;


import com.Matheuszin.domain.Profile;
import com.Matheuszin.domain.User;
import com.Matheuszin.domain.UserProfile;
import com.Matheuszin.repository.ProfileRepository;
import com.Matheuszin.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository repository;

    public List<UserProfile> findAll() {
        return repository.retrieveAll();
    }

    public List<User> findAllUsersByProfileId(Long profileId) {
        return repository.findAllUsersByProfileId(profileId);
    }
}

