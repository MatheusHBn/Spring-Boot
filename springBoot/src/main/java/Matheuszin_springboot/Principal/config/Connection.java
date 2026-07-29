package Matheuszin_springboot.Principal.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@ToString
@AllArgsConstructor

public class Connection {
    private String host;
    private String username;
    private String password;
}
