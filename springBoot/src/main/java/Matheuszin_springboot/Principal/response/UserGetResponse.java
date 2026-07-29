package Matheuszin_springboot.Principal.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserGetResponse {
    private Long id;
    private String firstName;
    private String lastName;
}
