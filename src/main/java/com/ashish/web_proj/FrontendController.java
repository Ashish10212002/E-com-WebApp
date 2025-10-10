package com.ashish.web_proj;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    // Forward all non-API routes to React index.html
    @RequestMapping(value = {"/{path:^(?!api).*}", "/**/{path:^(?!api).*}"})
    public String redirect() {
        return "forward:/index.html";
    }
}
