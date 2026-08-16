package Matheuszin_springboot.Principal.service;

import Matheuszin_springboot.Principal.Repository.MonitorHardCodedRepository;
import Matheuszin_springboot.Principal.domain.Monitor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class MonitorService {
    private MonitorHardCodedRepository repository;

    public MonitorService() {
        this.repository = new MonitorHardCodedRepository();
    }

    public List<Monitor> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Monitor findByIdOrThrowNotFound(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));
    }

    public Monitor save(Monitor monitor) {
        return repository.save(monitor);
    }

    public void deleteById(Long id) {
        var monitor = findByIdOrThrowNotFound(id);
        repository.deleteById(monitor);
    }

    public void update(Monitor monitorToUpdate) {
        var monitor = findByIdOrThrowNotFound(monitorToUpdate.getHertz());
        monitorToUpdate.setLocalDateTime(monitor.getLocalDateTime());
        repository.updateById(monitorToUpdate);
    }
}
