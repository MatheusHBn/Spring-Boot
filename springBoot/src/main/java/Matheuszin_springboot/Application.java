package Matheuszin_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "Matheuszin_springboot")
public class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
}

