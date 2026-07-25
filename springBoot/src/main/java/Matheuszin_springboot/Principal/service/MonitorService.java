package Matheuszin_springboot.Principal.service;

import Matheuszin_springboot.Principal.domain.Monitor;
import Matheuszin_springboot.Principal.repository.MonitorHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MonitorService {
    private final MonitorHardCodedRepository REPOSITORY;

    public List<Monitor> findAll(String name) {
        return name == null ? REPOSITORY.findAll() : REPOSITORY.findByName(name);
    }

    public Monitor findByHertzOrThrowNotFound(Long Hertz) {
        return REPOSITORY.findByHertz(Hertz).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Monitor not found"));
    }

    public Monitor save(Monitor monitor) {
        return REPOSITORY.save(monitor);
    }

    public void deleteByHertz(Long Hertz) {
        var monitor = findByHertzOrThrowNotFound(Hertz);
        REPOSITORY.deleteByHertz(monitor);
    }

    public void update(Monitor monitorToUpdate) {
        var monitor = findByHertzOrThrowNotFound(monitorToUpdate.getHertz());
        monitorToUpdate.setLocalDateTime(monitor.getLocalDateTime());
        REPOSITORY.updateByHertz(monitorToUpdate);
    }
}
