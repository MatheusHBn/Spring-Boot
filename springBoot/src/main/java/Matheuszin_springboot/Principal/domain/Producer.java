package Matheuszin_springboot.Principal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producer {
    @JsonProperty("full_name")
    private String name;
    @EqualsAndHashCode.Include
    private Long id;
    private LocalDateTime localDateTime;
}

