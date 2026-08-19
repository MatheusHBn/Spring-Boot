package Matheuszin_springboot.Principal.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MonitorPutRequest {
    private String name;
    private Long hertz;
}
