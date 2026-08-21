package Matheuszin_springboot.Principal.controller;

import Matheuszin_springboot.Principal.mapper.MonitorMapper;
import Matheuszin_springboot.Principal.request.MonitorPostRequest;
import Matheuszin_springboot.Principal.request.MonitorPutRequest;
import Matheuszin_springboot.Principal.response.MonitorGetResponse;
import Matheuszin_springboot.Principal.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/monitors")
public class MonitorController {
    private final MonitorMapper mapper;
    private final MonitorService service;

    @GetMapping("list")
    public ResponseEntity<List<MonitorGetResponse>> listAllMonitors(@RequestParam(required = false) String name) {
        var monitors = service.findAll(name);
        var monitorGetResponse = mapper.toMonitorGetResponseList(monitors);
        return ResponseEntity.ok(monitorGetResponse);
    }

    @GetMapping("{Hertz}")
    public ResponseEntity<MonitorGetResponse> findByHertz(@PathVariable Long Hertz) {
        var monitor = service.findByHertzOrThrowNotFound(Hertz);
        var monitorGetResponse = mapper.toMonitorGetResponse(monitor);
        return ResponseEntity.ok(monitorGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MonitorGetResponse> save(@RequestBody MonitorPostRequest monitorPostRequest, @RequestHeader HttpHeaders headers) {
        var monitor = mapper.toMonitor(monitorPostRequest);
        var monitorSaved = service.save(monitor);
        var produceGetResponse = mapper.toMonitorGetResponse(monitorSaved);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(produceGetResponse);
    }

    @DeleteMapping("{hertz}")
    public ResponseEntity<Void> deleteByHertz(@PathVariable Long hertz) {
        service.deleteByHertz(hertz);
        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody MonitorPutRequest request) {
        var updateMonitor = mapper.toMonitor(request);
        service.update(updateMonitor);
        return ResponseEntity.noContent().build();
    }
}
