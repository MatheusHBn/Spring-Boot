package com.Matheuszin.repository;

import com.Matheuszin.domain.User;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserData {
    private List<User> userList = new ArrayList<>(3);

    {
        var user = User.builder().id(1L).firstName("Matheus").lastName("Nascimento").email("matheus743@gmail.com").build();
        var user1 = User.builder().id(1L).firstName("Jacó").lastName("Silva").email("jaco2@hotmail.com").build();
        var user2 = User.builder().id(1L).firstName("Pedro").lastName("Martinez").email("pedropedro@yahoo.com").build();
        userList.addAll(List.of(user, user1, user2));
    }

    public List<User> getUser() {
        return userList;
    }
}
