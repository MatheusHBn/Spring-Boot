package com.Matheuszin.repository;

import com.Matheuszin.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findByNameIgnoreCase(String firstName);

    Optional<Profile> findByDescription(String description);

    Optional<Profile> findByDescriptionAndIdNot(String description, Long id);
}
