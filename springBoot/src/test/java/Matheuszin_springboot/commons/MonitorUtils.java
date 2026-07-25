package Matheuszin_springboot.commons;

import Matheuszin_springboot.Principal.domain.Monitor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MonitorUtils {
    public List<Monitor> newMonitorList() {
        String dateTime = "2026-07-25T01:13:53.5911426";
        var localDateTime = LocalDateTime.parse(dateTime);
        var alienware = new Monitor("Alienware", 75L, localDateTime);
        var lg = new Monitor("LG", 175L, localDateTime);
        var pichau = new Monitor("Pichau", 240L, localDateTime);
        return new ArrayList<>(List.of(alienware, lg, pichau));
    }

    public Monitor newMonitorToSave() {
        return Monitor.builder().hertz(99L).name("Alienware").localDateTime(LocalDateTime.now()).build();
    }
}
