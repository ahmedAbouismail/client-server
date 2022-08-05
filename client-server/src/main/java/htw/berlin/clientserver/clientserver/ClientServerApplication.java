package htw.berlin.clientserver.clientserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@ComponentScan("htw.berlin")
public class ClientServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServerApplication.class, args);
    }

    @Bean
    public WebClient.Builder WebClientBuilder() {
        return WebClient.builder();
    }

}
