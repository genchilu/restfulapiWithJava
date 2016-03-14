package url.genchi.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import url.genchi.controller.socketcli.SocketCli;

@RestController
@RequestMapping("/user")
public class UserRestController {
    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    private static JSONObject invalidJson = new JSONObject("{\"status\": \"failed\", \"message\": \"invalid input\"}");

    //only word and digit is a valid input
    private static void checkInput(String in) throws Exception {
        if(!in.matches("^(?i)[a-z0-9]*$")) {
            throw new Exception("invalid input");
        }
    }

    @RequestMapping(value="/{user}", method = RequestMethod.GET)
    public String getUser(@PathVariable("user") String username) throws InterruptedException, ExecutionException, IOException {
        try {
            checkInput(username);
        } catch (Exception e) {
            return invalidJson.toString();
        }
        Future<String> countryResult = executor.submit(new SocketCli("GET", username, "country", ""));
        Future<String> cityResult = executor.submit(new SocketCli("GET", username, "city", ""));
        String country = countryResult.get();
        String city = cityResult.get();
        JSONObject userdata = new JSONObject();
        userdata.put("username", username);
        userdata.put("country", country);
        userdata.put("city", city);
        return userdata.toString();
    }

    @RequestMapping(value="/{user}", method = RequestMethod.POST)
    public String postUser(@PathVariable("user") String username, @RequestParam("country") String country, @RequestParam("city") String city) throws InterruptedException, ExecutionException, IOException {
        try {
            checkInput(username);
            checkInput(country);
            checkInput(city);
        } catch (Exception e) {
            return invalidJson.toString();
        }
        Future<String> addCountryResult = executor.submit(new SocketCli("POST", username, "country", country));
        Future<String> addCityResult = executor.submit(new SocketCli("POST", username, "city", city));
        String addCountry = addCountryResult.get();
        String addCity = addCityResult.get();
        JSONObject mesg = new JSONObject();
        mesg.put("oper", "POST");
        if(addCountry.equals("SUCCESSED") || addCity.equals("SUCCESSED")) {
            mesg.put("status", "successed");
        } else {
            mesg.put("status", "failed");
        }
        return mesg.toString();
    }

    @RequestMapping(value="/{user}", method = RequestMethod.PUT)
    public String putUser(@PathVariable("user") String username, String country, String city) throws InterruptedException, ExecutionException, IOException {
        try {
            checkInput(username);
            checkInput(country);
            checkInput(city);
        } catch (Exception e) {
            return invalidJson.toString();
        }
        Future<String> putCountryResult = executor.submit(new SocketCli("PUT", username, "country", country));
        Future<String> putCityResult = executor.submit(new SocketCli("PUT", username, "city", city));
        String putCountry = putCountryResult.get();
        String putCity = putCityResult.get();
        JSONObject mesg = new JSONObject();
        mesg.put("oper", "PUT");
        if(putCountry.equals("SUCCESSED") || putCity.equals("SUCCESSED")) {
            mesg.put("status", "successed");
        } else {
            mesg.put("status", "failed");
        }
        return mesg.toString();
    }

    @RequestMapping(value="/{user}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("user") String username) throws InterruptedException, ExecutionException, IOException {
        try {
            checkInput(username);
        } catch (Exception e) {
            return invalidJson.toString();
        }
        Future<String> delCountryResult = executor.submit(new SocketCli("DELETE", username, "country", ""));
        Future<String> delCityResult = executor.submit(new SocketCli("GET", username, "city", ""));
        String delCountry = delCountryResult.get();
        String delCity = delCityResult.get();
        JSONObject mesg = new JSONObject();
        mesg.put("oper", "DELETE");
        if(delCountry.equals("SUCCESSED") || delCity.equals("SUCCESSED")) {
            mesg.put("status", "successed");
        } else {
            mesg.put("status", "failed");
        }
        return mesg.toString();
    }
}