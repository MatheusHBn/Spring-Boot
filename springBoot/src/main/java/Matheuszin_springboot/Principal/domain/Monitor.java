package Matheuszin_springboot.Principal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class Monitor {
    private String name;
    @EqualsAndHashCode.Include
    private Long hertz;
    private LocalDateTime localDateTime;

    public static List<Monitor> listMonitor() {
        var alienware = new Monitor("Alienware", 75L, LocalDateTime.now());
        var lg = new Monitor("LG", 175L, LocalDateTime.now());
        var pichau = new Monitor("Pichau", 240L, LocalDateTime.now());
        return List.of(alienware, lg, pichau);
    }
}

