package Matheuszin_springboot.Principal.repository;

import Matheuszin_springboot.Principal.domain.Producer;
import external.Dependecy.Connection;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Log4j2
public class ProducerHardCodedRepository {
    private final ProducerData producerData;


    public List<Producer> findAll() {
        return producerData.getProducers();
    }

    public Optional<Producer> findByID(Long id) {
        return producerData.getProducers().stream().filter(producer -> producer.getId().equals(id)).findFirst();
    }

    public List<Producer> findByName(String name) {
        return producerData.getProducers().stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
    }

    public Producer save(Producer producer) {
        producerData.getProducers().add(producer);
        return producer;
    }

    public void deleteById(Producer producer) {
        producerData.getProducers().remove(producer);
    }

    public void update(Producer producer) {
        deleteById(producer);
        save(producer);
    }
}
