package Matheuszin_springboot.controller.exercise02.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Monitor {
    private String name;
    private Long hertz;
    private static List<Monitor> monitorsList = new ArrayList<>();

    static{
        var alienware = new Monitor("Alienware", 75L);
        var lg = new Monitor("LG", 175L);
        var pichau = new Monitor("Pichau", 240L);
        monitorsList.addAll(List.of(alienware, lg, pichau));
    }

    public static List<Monitor> listMonitor() {
        var alienware = new Monitor("Alienware", 75L);
        var lg = new Monitor("LG", 175L);
        var pichau = new Monitor("Pichau", 240L);
        return List.of(alienware, lg, pichau);
    }

    public static List<Monitor> getMonitorsList() {
        return monitorsList;
    }
}
