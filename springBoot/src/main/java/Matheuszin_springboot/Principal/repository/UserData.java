package Matheuszin_springboot.Principal.repository;

import Matheuszin_springboot.Principal.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserData {
    private List<User> userList = new ArrayList<>();

    {
        var user = new User(1L, "Matheus", "Henrique", "matheuszin64536@gmail.com");
        var user1 = new User(2L, "Pedro", "Hossaka", "pedrinz@outlook.com");
        var user2 = new User(3L, "Geovane", "Farias", "GeovaneGomes@yahoo.com");
        userList.addAll(List.of(user, user1, user2));
    }

    public List<User> getUserList() {
        return userList;
    }
}
