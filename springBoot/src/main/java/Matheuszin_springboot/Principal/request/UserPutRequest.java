package Matheuszin_springboot.Principal.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserPutRequest {
    @NotBlank(message = "the field 'id' is required" )
    @NotNull
    private Long id;
    private String firstName;
    private String lastName;
    @Email()
    private String email;
}
