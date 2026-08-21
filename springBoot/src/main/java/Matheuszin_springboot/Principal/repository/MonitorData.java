package Matheuszin_springboot.Principal.repository;

import Matheuszin_springboot.Principal.domain.Monitor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MonitorData {
    private  List<Monitor> monitorList = new ArrayList<>();

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
