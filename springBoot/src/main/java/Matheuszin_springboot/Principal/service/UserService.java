package Matheuszin_springboot.Principal.service;

import Matheuszin_springboot.Principal.domain.User;
import Matheuszin_springboot.Principal.repository.UserHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserHardCodedRepository repository;

    public List<User> findAllUser() {
        return repository.findAllUsers();
    }

    public List<User> findAllUserFirstName(String firstName) {
        return firstName == null ? repository.findAllUsers() : repository.findByFirstNameUser(firstName);
    }

    public List<User> findAllUserLastName(String lastName) {
        return lastName == null ? repository.findAllUsers() : repository.findByLastNameUser(lastName);
    }

    public User findByIdOrThrowNotFoundUser(Long id) {
        return repository.findByIdUser(id).orElseThrow(() -> new Matheuszin_springboot.exception.NotFoundException("User not found"));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void deleteByIdUser(Long id) {
        var user = findByIdOrThrowNotFoundUser(id);
        repository.deleteByIdUser(user);
    }

    public void update(User userToUpdate) {
        var user = findByIdOrThrowNotFoundUser(userToUpdate.getId());
        repository.update(user);
    }
}
