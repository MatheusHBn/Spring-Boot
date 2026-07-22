package Matheuszin_springboot.Principal.request;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MonitorPutRequest {
    private String name;
    private Long hertz;
}
