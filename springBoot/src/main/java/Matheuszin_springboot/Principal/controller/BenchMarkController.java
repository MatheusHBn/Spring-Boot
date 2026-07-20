package Matheuszin_springboot.Principal.controller;

import Matheuszin_springboot.ExerciciosPratica.domain.Monitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("v1")
@Slf4j
public class BenchMarkController {

    @GetMapping("monitores")
    public List<Monitor> lista() throws InterruptedException {
        log.info(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(1);
        return List.of(new Monitor("AlienWare", 165), new Monitor("Pichau", 144), new Monitor("Manager", 75));
    }
}
