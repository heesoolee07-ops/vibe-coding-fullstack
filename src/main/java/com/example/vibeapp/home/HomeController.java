package com.example.vibeapp.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String index() {
        return "home/home";
    }

    @GetMapping("/api/hello")
    @ResponseBody
    public String hello() {
        return "Hello, Vibe!";
    }
}
