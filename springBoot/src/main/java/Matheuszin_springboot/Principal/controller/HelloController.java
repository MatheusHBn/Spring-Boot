package Matheuszin_springboot.Principal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(value = "greeting")
@Slf4j
public class HelloController {

    @GetMapping("go")
    public String hi() {
        return "Salve mundo";
    }

    @PostMapping()
    public Long save(@RequestBody String name) {
        log.info("save '{}'", name);
        return ThreadLocalRandom.current().nextLong(1, 1000);
    }
}
