package Matheuszin_springboot.Principal.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonitorPostRequest {
    @NotBlank(message = "this field is required")
    private String name;
}
