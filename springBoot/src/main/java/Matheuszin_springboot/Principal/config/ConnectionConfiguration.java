package Matheuszin_springboot.Principal.config;

import external.Dependecy.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {

    @Bean
    public Connection connection() {
        return new Connection("Eu memo", "Matheuszin ", "cinqueta e dois");
    }
}
