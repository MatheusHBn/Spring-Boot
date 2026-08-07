package com.Matheuszin.service;


import com.Matheuszin.domain.User;
import com.Matheuszin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<User> findAll(String firstName) {
        return firstName == null ? repository.findAll() : repository.findByFirstNameIgnoreCase(firstName);
    }

    public Page<User> findAllPaged(Pageable pageable) {
         return repository.findAll(pageable);
    }

    public User findByIdOrThrowNotFound(Long id) {
        return repository.findById(id).orElseThrow(() -> new Matheuszin_springboot.exception.NotFoundException("User not found"));
    }

    public User save(User user){
        assertEmailDoesntExist(user.getEmail());
        return repository.save(user);
    }

    public void delete(Long id){
        var user = findByIdOrThrowNotFound(id);
        repository.delete(user);
    }

    public void update(User userToUpdate){
        assertUserExists(userToUpdate.getId());
        assertEmailDoesntExist(userToUpdate.getEmail());
        repository.save(userToUpdate);
    }

    public void assertUserExists(Long id){
       findByIdOrThrowNotFound(id);
    }

    public void assertEmailDoesntExist(String email){
        repository.findByEmail(email).ifPresent(this::throwEmailExistsExcepiton);
    }

    public void assertEmailDoesntExist(String email, Long id){
        repository.findByEmailAndIdNot(email, id).ifPresent(this::throwEmailExistsExcepiton);
    }

    private void throwEmailExistsExcepiton(User user){
        throw new ResponseStatusException(BAD_REQUEST, "E-mail %s already exists".formatted(user.getEmail()));
    }
}
