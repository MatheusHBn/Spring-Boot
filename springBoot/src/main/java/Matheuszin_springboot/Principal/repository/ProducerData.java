package Matheuszin_springboot.Principal.repository;


import Matheuszin_springboot.Principal.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerData {
    private List<Producer> producers = new ArrayList<>();

    {
        var apple = new Producer("Apple", 1L, LocalDateTime.now());
        var google = new Producer("Google", 2L, LocalDateTime.now());
        var amazon = new Producer("Amazon", 3L, LocalDateTime.now());
        producers.addAll(List.of(apple, google, amazon));
    }

    public List<Producer> getProducers() {
        return producers;
    }
}
