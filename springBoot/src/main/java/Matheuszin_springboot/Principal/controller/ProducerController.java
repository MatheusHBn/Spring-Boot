package Matheuszin_springboot.Principal.controller;

import Matheuszin_springboot.Principal.Response.ProducerGetResponse;
import Matheuszin_springboot.Principal.mapper.ProducerMapper;
import Matheuszin_springboot.Principal.request.ProducerPostRequest;
import Matheuszin_springboot.Principal.request.ProducerPutRequest;
import Matheuszin_springboot.Principal.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {
    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;
    private ProducerService service;

    public ProducerController() {
        this.service = new ProducerService();
    }

    @GetMapping("list")
    public ResponseEntity<List<ProducerGetResponse>> listAllProducers(@RequestParam(required = false) String name) {
        var producers = service.findAll(name);
        var producerGetResponses = MAPPER.toProducerGetResponseList(producers);
        return ResponseEntity.ok(producerGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {
        var producer = service.findByIdOrThrowNotFound(id);
        var producerGetResponse = MAPPER.toProducerGetResponse(producer);
        return ResponseEntity.ok(producerGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        log.info("'{}'", headers);
        var producer = MAPPER.toProducer(producerPostRequest);
        var producersaved = service.save(producer);
        var producerGetResponse = MAPPER.toProducerGetResponse(producersaved);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(producerGetResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        var updateProducer = MAPPER.toProducer(request);
        service.update(updateProducer);
        return ResponseEntity.noContent().build();
    }
}
