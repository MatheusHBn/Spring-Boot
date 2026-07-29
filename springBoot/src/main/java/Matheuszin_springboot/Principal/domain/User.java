package Matheuszin_springboot.Principal.domain;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class User {
    @EqualsAndHashCode.Include
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

}
