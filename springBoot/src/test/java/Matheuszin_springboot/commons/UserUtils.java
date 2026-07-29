package Matheuszin_springboot.commons;

import Matheuszin_springboot.Principal.domain.Producer;
import Matheuszin_springboot.Principal.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {
    public List<User> newUserList(){
        var user = new User(1L, "Matheus", "Henrique", "matheuszin64536@gmail.com");
        var user1 = new User(2L, "Pedro", "Hossaka", "pedrinz@outlook.com");
        var user2 = new User(3L, "Geovane", "Farias", "GeovaneGomes@yahoo.com");
        return new ArrayList<>(List.of(user,user1,user2));
    }

    public User newUserToSave(){
        return User.builder().id(1L).firstName("Matheus").lastName("Henrique").email("matehsuiun@gmail.com").build();
    }
}
