package Matheuszin_springboot.ExerciciosPratica.controller;

import Matheuszin_springboot.Principal.domain.Monitor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("loja/monitores")
public class MonitorController01 {


    @GetMapping
    public List<Monitor> monitorList() {
        return Monitor.listMonitor();
    }

    @GetMapping("filtro-nome")
    public List<Monitor> nameFilter(@RequestParam(value = "name") String name) {
        return Monitor.listMonitor().stream().filter(monitor -> name.equals(monitor.getName())).toList();
    }

    @GetMapping("hertz/{hertz}")
    public List<Monitor> hertzFilter(@PathVariable Long hertz) {
        return Monitor.listMonitor().stream().filter(monitor -> hertz.equals(monitor.getHertz())).toList();
    }
}
