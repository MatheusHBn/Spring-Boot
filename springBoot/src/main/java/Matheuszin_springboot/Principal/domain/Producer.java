package Matheuszin_springboot.Principal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Producer {
    @JsonProperty("full_name")
    private String name;
    @EqualsAndHashCode.Include
    @Id
    private Long id;
    private LocalDateTime localDateTime;
}

