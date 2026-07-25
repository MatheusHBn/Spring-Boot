package Matheuszin_springboot.commons;

import Matheuszin_springboot.Principal.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerUtils {
    public List<Producer> newProducerList(){
        String dateTime = "2026-07-24T01:57:42.0274804";
        var localDateTime = LocalDateTime.parse(dateTime);
        var nubank = new Producer("Nubank", 1L, localDateTime);
        var itau = new Producer("Itaú", 2L, localDateTime);
        var c6Bank = new Producer("C6 Bank", 3L, localDateTime);
        return new ArrayList<>(List.of(nubank, itau, c6Bank));
    }

    public Producer newProducerToSave(){
        return Producer.builder().id(99L).name("Nubank").localDateTime(LocalDateTime.now()).build();
    }
}
