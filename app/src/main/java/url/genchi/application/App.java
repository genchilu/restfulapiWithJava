package url.genchi.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan( basePackages = {"url.genchi.application", "url.genchi.controller"} )
public class App {
        public static void main(String[] args) {
            SpringApplication.run(App.class, args);
        }
}
