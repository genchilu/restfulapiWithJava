package url.genchi.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = {"url.genchi.controller", "url.genchi.websocket"} )
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}