package url.genchi.controller;

import org.springframework.stereotype.Controller;

@Controller
public class IndexController {
    //@RequestMapping("/")
    public String index() {
        return "index.html";
    }
}
