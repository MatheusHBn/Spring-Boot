package Matheuszin_springboot.Principal.Repository;

import Matheuszin_springboot.Principal.domain.Producer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerHardCodedRepository {
    private static List<Producer> PRODUCERS = new ArrayList<>();

    static {
        var alienware = new Producer("Alienware", 75L, LocalDateTime.now());
        var lg = new Producer("LG", 175L, LocalDateTime.now());
        var pichau = new Producer("Pichau", 240L, LocalDateTime.now());
        PRODUCERS.addAll(List.of(alienware, lg, pichau));
    }

    public  List<Producer> findAll() {
        return PRODUCERS;
    }

    public Optional<Producer> findByID(Long id){
        return PRODUCERS.stream().filter(producer -> producer.getId().equals(id)).findFirst();
    }

    public List<Producer> findByName(String name){
        return PRODUCERS.stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
    }

    public Producer save(Producer producer){
        PRODUCERS.add(producer);
        return producer;
    }

    public void deleteById(Producer producer){
        PRODUCERS.remove(producer);
    }

    public void update(Producer producer){
        deleteById(producer);
        save(producer);
    }
}
