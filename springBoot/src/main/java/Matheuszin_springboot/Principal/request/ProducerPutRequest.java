package Matheuszin_springboot.Principal.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProducerPutRequest {
    private String name;
    private Long id;

}
