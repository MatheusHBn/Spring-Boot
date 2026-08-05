package Matheuszin_springboot.controller;

import Matheuszin_springboot.controller.exercise02.domain.Monitor;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("v1/monitors")
public class MonitorController {
    private static final List<String> monitors = List.of("AlienWare", "Pichau", "Manager", "LG");


    @GetMapping()
    public List<String> listAllMonitors(){
        return monitors;
    }

    @GetMapping("filter")
    public List<String> listAllMonitorsParam(@RequestParam(defaultValue = "") String name){
        return monitors.stream().filter(monitor -> monitor.equalsIgnoreCase(name)).toList();
    }

    @GetMapping("filterList")
    public List<String> listAllMonitorsParamList(@RequestParam(defaultValue = "") List<String> names){
        return monitors.stream().filter(names::contains).toList();
    }

    @GetMapping("{name}")
    public String findByName(@PathVariable()String name){
        return monitors.stream().filter(monitor -> monitor.equalsIgnoreCase(name)).findFirst().orElse("");
    }

    @PostMapping()
    public Monitor save(@RequestBody Monitor monitor){
        monitor.setHertz(ThreadLocalRandom.current().nextLong(1000));
        Monitor.getMonitorsList().add(monitor);
        return monitor;
    }
}
