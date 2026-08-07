package com.Matheuszin.commons;

import com.Matheuszin.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {
    public List<User> newUserList() {
        var user = User.builder().id(1L).firstName("Matheus").lastName("Nascimento").email("matheus743@gmail.com").build();
        var user1 = User.builder().id(2L).firstName("Jacó").lastName("Silva").email("jaco2@hotmail.com").build();
        var user2 = User.builder().id(3L).firstName("Pedro").lastName("Martinez").email("pedropedro@yahoo.com").build();

        return new ArrayList<>(List.of(user, user1, user2));
    }

    public User newUserToSave() {
        return User.builder().id(34L).firstName("Thiago").lastName("Nascimento").email("thiagozaozao@outlook.com").build();
    }

    public User newUserSaved() {
        return User.builder().id(34L).firstName("Thiago").lastName("Nascimento").email("thiagozaozao@outlook.com").build();
    }

    public User newUserToSaveDB() {
        return User.builder().firstName("João").lastName("Silva").email("joao4@outlook.com").build();
    }
}
