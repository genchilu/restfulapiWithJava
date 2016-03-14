package url.genchi.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = {"url.genchi.controller", "url.genchi"} )
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}