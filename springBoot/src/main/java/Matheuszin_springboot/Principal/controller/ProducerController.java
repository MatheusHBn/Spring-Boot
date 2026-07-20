package Matheuszin_springboot.Principal.controller;

import Matheuszin_springboot.Principal.domain.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {
    private static final List<Producer> producer = Producer.getProducerList();


    @GetMapping()
    public List<Producer> listAllMonitors(){
        return producer;
    }

    @GetMapping("filter")
    public List<Producer> listAllMonitorsParam(@RequestParam(defaultValue = "") String name){
        return producer.stream().filter(monitor -> monitor.getName().equals(name)).toList();
    }

    @GetMapping("filterList")
    public List<Producer> listAllMonitorsParamList(@RequestParam(defaultValue = "") List<String> names){
        return producer.stream().filter(names::contains).toList();
    }

    @GetMapping("{name}")
    public Producer findByName(@PathVariable()String name){
        return producer.stream().filter(monitor -> monitor.getName().equals(name)).findFirst().orElse(null);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Producer> save(@RequestBody Producer producer, @RequestHeader HttpHeaders headers){
        log.info("'{}'", headers);
        headers.add("Authorization", "My key");
        producer.setId(ThreadLocalRandom.current().nextLong(1000));
        Producer.getProducerList().add(producer);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(producer);
    }
}
