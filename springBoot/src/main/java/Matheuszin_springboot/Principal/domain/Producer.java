package Matheuszin_springboot.Principal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Producer {
    @JsonProperty("full_name")
    private String name;
    private Long id;
    private LocalDateTime localDateTime;
    private static List<Producer> producerList = new ArrayList<>();

    static{
        var amazon = new Producer("Amazon", 75L, LocalDateTime.now());
        var google = new Producer("Google", 175L, LocalDateTime.now());
        var apple = new Producer("Apple", 240L, LocalDateTime.now());
        producerList.addAll(List.of(amazon, google, apple));
    }

    public static List<Producer> listMonitor() {
        var amazon = new Producer("Amazon", 75L, LocalDateTime.now());
        var google = new Producer("Google", 175L, LocalDateTime.now());
        var apple = new Producer("Apple", 240L, LocalDateTime.now());
        return List.of(amazon, google, apple);
    }

    public static List<Producer> getProducerList() {
        return producerList;
    }
}
