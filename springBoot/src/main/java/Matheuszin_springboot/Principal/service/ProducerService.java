package Matheuszin_springboot.Principal.service;

import Matheuszin_springboot.Principal.Repository.ProducerHardCodedRepository;
import Matheuszin_springboot.Principal.domain.Producer;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class ProducerService {
    private ProducerHardCodedRepository repository;

    public ProducerService() {
        this.repository = new ProducerHardCodedRepository();
    }

    public List<Producer> findAll(String name){
        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Producer findByIdOrThrowNotFound(Long id){
        return repository.findByID(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));
    }

    public Producer save(Producer producer){
        return repository.save(producer);
    }

    public void deleteById(Long id){
        var producer = findByIdOrThrowNotFound(id);
        repository.deleteById(producer);
    }

    public void update(Producer producerToUpdate){
        var producer = findByIdOrThrowNotFound(producerToUpdate.getId());
        producerToUpdate.setLocalDateTime(producer.getLocalDateTime());
        repository.update(producerToUpdate);
    }
}
