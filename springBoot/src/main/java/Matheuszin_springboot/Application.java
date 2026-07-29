package Matheuszin_springboot;

import Matheuszin_springboot.Principal.config.ConnectionConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = ConnectionConfigurationProperties.class)
public class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

