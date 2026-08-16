package Matheuszin_springboot.Principal.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class MonitorGetResponse {
    private String name;
    private Long hertz;
    private LocalDateTime localDateTime;
}
