package Matheuszin_springboot.Principal.controller;

import Matheuszin_springboot.Principal.mapper.ProducerMapper;
import Matheuszin_springboot.Principal.request.ProducerPostRequest;
import Matheuszin_springboot.Principal.request.ProducerPutRequest;
import Matheuszin_springboot.Principal.response.ProducerGetResponse;
import Matheuszin_springboot.Principal.service.ProducerService;
import Matheuszin_springboot.Principal.config.Connection;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ProducerController {
    private final ProducerMapper mapper;
    private final ProducerService service;
//    private final Connection connection;

    @GetMapping("list")
    public ResponseEntity<List<ProducerGetResponse>> findAll(@RequestParam(required = false) String name) {
        var producers = service.findAll(name);
        var producerGetResponses = mapper.toProducerGetResponseList(producers);
        return ResponseEntity.ok(producerGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> findById(@PathVariable Long id) {
        var producer = service.findByIdOrThrowNotFound(id);
        var producerGetResponse = mapper.toProducerGetResponse(producer);
        return ResponseEntity.ok(producerGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        log.info("'{}'", headers);
        var producer = mapper.toProducer(producerPostRequest);
        var producersaved = service.save(producer);
        var producerGetResponse = mapper.toProducerGetResponse(producersaved);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(producerGetResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        var updateProducer = mapper.toProducer(request);
        service.update(updateProducer);
        return ResponseEntity.noContent().build();
    }
}
