package Matheuszin_springboot.Principal.repository;

import Matheuszin_springboot.Principal.domain.Monitor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MonitorHardCodedRepository {
    private static final List<Monitor> MONITORLIST = new ArrayList<>();

    static {
        var alienware = new Monitor("Alienware", 75L, LocalDateTime.now());
        var lg = new Monitor("LG", 175L, LocalDateTime.now());
        var pichau = new Monitor("Pichau", 240L, LocalDateTime.now());
        MONITORLIST.addAll(List.of(alienware, lg, pichau));
    }

    public List<Monitor> findAll() {
        return MONITORLIST;
    }

    public Optional<Monitor> findById(Long hertz) {
        return MONITORLIST.stream().filter(monitor -> monitor.getHertz().equals(hertz)).findFirst();
    }

    public List<Monitor> findByName(String name) {
        return MONITORLIST.stream().filter(monitor -> monitor.getName().equalsIgnoreCase(name)).toList();
    }

    public Monitor save(Monitor monitor) {
        MONITORLIST.add(monitor);
        return monitor;
    }

    public void deleteById(Monitor monitor) {
        MONITORLIST.remove(monitor);
    }

    public void updateById(Monitor monitor) {
        deleteById(monitor);
        save(monitor);
    }
}
