package url.genchi.controller;

import hello.Greeting;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import url.genchi.user.UserData;

@RestController
@RequestMapping("/user")
public class UserRestController {
    //@RequestMapping(value="/{user}")
    //@RequestMapping("/user")
    @RequestMapping("/user")
    @MessageMapping("/user")
    @SendTo("/sender/greetings")
    public Greeting greeting(UserData message) throws Exception {
        System.out.println("in");
        Thread.sleep(3000); // simulated delay
        return new Greeting("GET, " + message.getUsername() + " " + message.getAddress() + " " + Integer.toString(message.getAge()) + "!");
    }

}