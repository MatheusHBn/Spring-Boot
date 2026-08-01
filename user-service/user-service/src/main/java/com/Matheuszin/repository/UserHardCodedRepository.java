package com.Matheuszin.repository;

import com.Matheuszin.domain.User;
import com.Matheuszin.response.UserPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserHardCodedRepository {
    private final UserData userData;

    public List<User> findAll() {
        return userData.getUser();
    }

    public List<User> findByFirstName(String firstName) {
        return userData.getUser().stream().filter(User -> User.getFirstName().equalsIgnoreCase(firstName)).toList();
    }

    public Optional<User> findById(Long id) {
        return userData.getUser().stream().filter(User -> User.getId().equals(id)).findFirst();
    }

    public User save(User user) {
        userData.getUser().add(user);
        return user;
    }

    public void delete(User user) {
        userData.getUser().remove(user);
    }

    public void update(User user) {
        delete(user);
        save(user);
    }
}
