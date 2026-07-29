//package Matheuszin_springboot.Principal.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//@Configuration
//public class ConnectionConfiguration {
//    private ConnectionConfigurationProperties connectionConfigurationProperties;
//
//    @Value("${database.mysql.url}")
//    private String url;
//    @Value("${database.mysql.password}")
//    private String password;
//    @Value("${database.mysql.username}")
//    private String username;
//    @Value("${database.test.url}")
//    private String urlTest;
//    @Value("${database.test.password}")
//    private String passwordTest;
//    @Value("${database.test.username}")
//    private String usernameTest;

//    @Bean
//    @Profile("mysql")
//    public Connection connection() {
//        return new Connection(connection().getUsername(), connectionConfigurationProperties.password(), connectionConfigurationProperties.url());
//    }
//    @Bean
//    @Profile("test")
//    public Connection connectionTest() {
//        return new Connection(urlTest, passwordTest, usernameTest);
//    }
//}


