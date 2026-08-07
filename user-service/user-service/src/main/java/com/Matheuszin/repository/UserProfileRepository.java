package com.Matheuszin.repository;

import com.Matheuszin.domain.User;
import com.Matheuszin.domain.UserProfile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

//    @EntityGraph(attributePaths = {"user", "profile", "user", "profile"})
    @Query("SELECT up from UserProfile up join fetch up.user u join fetch up.profile p")
    List<UserProfile> retrieveAll();


    @EntityGraph(value = "UserProfile.fullDetails")
    List<UserProfile> findAll();

    @Query("SELECT up.user from UserProfile up where up.profile.id = ?1")
    List<User> findAllUsersByProfileId(Long id);
}
