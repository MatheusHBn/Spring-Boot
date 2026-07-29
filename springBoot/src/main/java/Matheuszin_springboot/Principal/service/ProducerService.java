package Matheuszin_springboot.Principal.service;

import Matheuszin_springboot.Principal.domain.Producer;
import Matheuszin_springboot.Principal.repository.ProducerHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProducerService {
    private final ProducerHardCodedRepository REPOSITORY;

    public List<Producer> findAll(String name) {
        return name == null ? REPOSITORY.findAll() : REPOSITORY.findByName(name);
    }

    public Producer findByIdOrThrowNotFound(Long id) {
        return REPOSITORY.findByID(id).orElseThrow(() -> new Matheuszin_springboot.exception.NotFoundException("Producer not found"));
    }

    public Producer save(Producer producer) {
        return REPOSITORY.save(producer);
    }

    public void deleteById(Long id) {
        var producer = findByIdOrThrowNotFound(id);
        REPOSITORY.deleteById(producer);
    }

    public void update(Producer producerToUpdate) {
        var producer = findByIdOrThrowNotFound(producerToUpdate.getId());
        producerToUpdate.setLocalDateTime(producer.getLocalDateTime());
        REPOSITORY.update(producerToUpdate);
    }
}
