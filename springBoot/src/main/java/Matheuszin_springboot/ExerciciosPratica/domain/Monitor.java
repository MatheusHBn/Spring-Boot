package Matheuszin_springboot.ExerciciosPratica.domain;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class Monitor {
    String nome;
    int hertz;
}
