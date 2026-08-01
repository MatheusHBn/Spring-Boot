package Matheuszin_springboot.Principal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Monitor {
    private String name;
    @EqualsAndHashCode.Include
    @Id
    private Long hertz;
    private LocalDateTime localDateTime;

    public static List<Monitor> listMonitor() {
        var alienware = new Monitor("Alienware", 75L, LocalDateTime.now());
        var lg = new Monitor("LG", 175L, LocalDateTime.now());
        var pichau = new Monitor("Pichau", 240L, LocalDateTime.now());
        return List.of(alienware, lg, pichau);
    }
}

