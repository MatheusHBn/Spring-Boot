package Matheuszin_springboot.Principal.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MonitorPutRequest {
    @NotBlank(message = "this field is required")
    private String name;
    @NotNull
    private Long hertz;
}
