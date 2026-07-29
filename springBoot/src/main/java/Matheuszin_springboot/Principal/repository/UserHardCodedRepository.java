package Matheuszin_springboot.Principal.repository;

import Matheuszin_springboot.Principal.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserHardCodedRepository {
    private final UserData userData;

    public List<User> findAllUsers(){
        return userData.getUserList();
    }

    public Optional<User> findByIdUser(Long id){
        return userData.getUserList().stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public List<User> findByFirstNameUser(String firstName){
        return userData.getUserList().stream().filter(user -> user.getFirstName().equalsIgnoreCase(firstName)).toList();
    }

    public List<User> findByLastNameUser(String lastName){
        return userData.getUserList().stream().filter(user -> user.getLastName().equalsIgnoreCase(lastName)).toList();
    }
    public List<User> findByEmailUser(String email){
        return userData.getUserList().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).toList();
    }

    public User save(User user){
        userData.getUserList().add(user);
        return user;
    }

    public void deleteByIdUser(User user){
        userData.getUserList().remove(user);
    }

    public void update(User user){
        deleteByIdUser(user);
        save(user);
    }
}
