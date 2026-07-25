package Matheuszin_springboot.Principal.repository;

import Matheuszin_springboot.Principal.domain.Monitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MonitorHardCodedRepository {
    private final MonitorData monitorData;

    public List<Monitor> findAll() {
        return monitorData.getMonitorList();
    }

    public Optional<Monitor> findByHertz(Long hertz) {
        return monitorData.getMonitorList().stream().filter(monitor -> monitor.getHertz().equals(hertz)).findFirst();
    }

    public List<Monitor> findByName(String name) {
        return monitorData.getMonitorList().stream().filter(monitor -> monitor.getName().equalsIgnoreCase(name)).toList();
    }

    public Monitor save(Monitor monitor) {
        monitorData.getMonitorList().add(monitor);
        return monitor;
    }

    public void deleteByHertz(Monitor monitor) {
        monitorData.getMonitorList().remove(monitor);
    }

    public void updateByHertz(Monitor monitor) {
        deleteByHertz(monitor);
        save(monitor);
    }
}
