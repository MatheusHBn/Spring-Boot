package Matheuszin_springboot.Principal.controller;

import Matheuszin_springboot.Principal.Response.MonitorGetResponse;
import Matheuszin_springboot.Principal.mapper.MonitorMapper;
import Matheuszin_springboot.Principal.request.MonitorPostRequest;
import Matheuszin_springboot.Principal.request.MonitorPutRequest;
import Matheuszin_springboot.Principal.service.MonitorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("v1/monitors")
public class MonitorController {
    private static final MonitorMapper MAPPER = MonitorMapper.INSTANCE;
    private MonitorService service;

    public MonitorController() {
        this.service = new MonitorService();
    }

    @GetMapping("list")
    public ResponseEntity<List<MonitorGetResponse>> listAllMonitors(@RequestParam(required = false) String name) {
        var monitors = service.findAll(name);
        var monitorGetResponse = MAPPER.toMonitorGetResponseList(monitors);
        return ResponseEntity.ok(monitorGetResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<MonitorGetResponse> findById(@PathVariable Long id) {
        var monitor = service.findByIdOrThrowNotFound(id);
        var monitorGetResponse = MAPPER.toMonitorGetResponse(monitor);
        return ResponseEntity.ok(monitorGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MonitorGetResponse> save(@RequestBody MonitorPostRequest monitorPostRequest, @RequestHeader HttpHeaders headers) {
        var monitor = MAPPER.toMonitor(monitorPostRequest);
        var monitorSaved = service.save(monitor);
        var produceGetResponse = MAPPER.toMonitorGetResponse(monitorSaved);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(produceGetResponse);
    }

    @DeleteMapping("{hertz}")
    public ResponseEntity<Void> deleteById(@PathVariable Long hertz) {
        service.deleteById(hertz);
        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody MonitorPutRequest request) {
        var updateMonitor = MAPPER.toMonitor(request);
        service.update(updateMonitor);
        return ResponseEntity.noContent().build();
    }
}
