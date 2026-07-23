package Matheuszin_springboot.Principal.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MonitorPostResponse {
    private String name;
    private Long hertz;
}
