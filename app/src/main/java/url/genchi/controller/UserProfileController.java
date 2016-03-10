package url.genchi.controller;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserProfileController {
    @RequestMapping(value="/{user}", method=RequestMethod.GET)
    @SendTo("/")
    public String getUser(@PathVariable String user) throws InterruptedException {
        Thread.sleep(3000);
        return "GET " + user;
    }

    @RequestMapping(value="/{user}", method=RequestMethod.POST)
    public String addUser(@PathVariable String user) {
        return "POST " + user;
    }

    @RequestMapping(value="/{user}", method=RequestMethod.PUT)
    public String updateUser(@PathVariable String user) {
        return "PUT " + user;
    }

    @RequestMapping(value="/{user}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable String user) {
        return "DELETE " + user;
    }
}
