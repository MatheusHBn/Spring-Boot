package Matheuszin_springboot.Principal.repository;

import Matheuszin_springboot.Principal.domain.Monitor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MonitorData {
    private final List<Monitor> monitorList = new ArrayList<>();

    {
        var alienware = new Monitor("Alienware", 75L, LocalDateTime.now());
        var lg = new Monitor("LG", 175L, LocalDateTime.now());
        var pichau = new Monitor("Pichau", 240L, LocalDateTime.now());
        monitorList.addAll(List.of(alienware, lg, pichau));
    }

    public List<Monitor> getMonitorList() {
        return monitorList;
    }
}
