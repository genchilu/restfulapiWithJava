package url.genchi.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import url.genchi.controller.socketcli.SocketCli;

import com.google.common.base.Stopwatch;

@RestController
@RequestMapping("/user")
public class UserRestController {
    @Autowired private SocketCli socketcli;

    @RequestMapping(value="/{user}", method = RequestMethod.GET)
    public String getUser(@PathVariable("user") String username) throws InterruptedException, ExecutionException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Future<String> countryResult = socketcli.sendCmdAsync("TW");
        Future<String> cityResult = socketcli.sendCmdAsync("KH");
        String country = countryResult.get();
        String city = cityResult.get();
        stopwatch.elapsed(TimeUnit.MILLISECONDS);
        JSONObject userdata = new JSONObject();
        userdata.put("username", username);
        userdata.put("country", country);
        userdata.put("city", city);
        return userdata.toString();
    }

    @RequestMapping(value="/{user}", method = RequestMethod.POST)
    public String postUser(@PathVariable("user") String username, String country, String city) throws InterruptedException, ExecutionException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Future<String> countryResult = socketcli.sendCmdAsync("TW");
        Future<String> cityResult = socketcli.sendCmdAsync("KH");

        stopwatch.elapsed(TimeUnit.MILLISECONDS);
        JSONObject mesg = new JSONObject();
        mesg.put("oper", "POST");
        mesg.put("status", "successed");
        return mesg.toString();
    }

    @RequestMapping(value="/{user}", method = RequestMethod.PUT)
    public String putUser(@PathVariable("user") String username, String country, String city) throws InterruptedException, ExecutionException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Future<String> countryResult = socketcli.sendCmdAsync("TW");
        Future<String> cityResult = socketcli.sendCmdAsync("KH");

        stopwatch.elapsed(TimeUnit.MILLISECONDS);
        JSONObject mesg = new JSONObject();
        mesg.put("oper", "PUT");
        mesg.put("status", "successed");
        return mesg.toString();
    }

    @RequestMapping(value="/{user}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("user") String username, String country, String city) throws InterruptedException, ExecutionException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Future<String> countryResult = socketcli.sendCmdAsync("TW");
        Future<String> cityResult = socketcli.sendCmdAsync("KH");

        stopwatch.elapsed(TimeUnit.MILLISECONDS);
        JSONObject mesg = new JSONObject();
        mesg.put("oper", "DELETE");
        mesg.put("status", "successed");
        return mesg.toString();
    }
}