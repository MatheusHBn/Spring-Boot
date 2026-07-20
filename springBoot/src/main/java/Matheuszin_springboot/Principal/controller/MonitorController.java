package Matheuszin_springboot.Principal.controller;

import Matheuszin_springboot.Principal.domain.Monitor;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/monitors")
public class MonitorController {
    private static final List<Monitor> monitors = Monitor.getMonitorsList();


    @GetMapping()
    public List<Monitor> listAllMonitors(){
        return monitors;
    }

    @GetMapping("filter")
    public List<Monitor> listAllMonitorsParam(@RequestParam(defaultValue = "") String name){
        return monitors.stream().filter(monitor -> monitor.getName().equals(name)).toList();
    }

    @GetMapping("filterList")
    public List<Monitor> listAllMonitorsParamList(@RequestParam(defaultValue = "") List<String> names){
        return monitors.stream().filter(names::contains).toList();
    }

    @GetMapping("{name}")
    public Monitor findByName(@PathVariable()String name){
        return monitors.stream().filter(monitor -> monitor.getName().equals(name)).findFirst().orElse(null);
    }

    @PostMapping()
    public Monitor save(@RequestBody Monitor monitor){
        monitor.setHertz(ThreadLocalRandom.current().nextLong(1000));
        Monitor.getMonitorsList().add(monitor);
        return monitor;
    }
}
